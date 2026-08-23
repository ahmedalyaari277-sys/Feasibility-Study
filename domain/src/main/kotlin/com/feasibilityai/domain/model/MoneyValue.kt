package com.feasibilityai.domain.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Represents a single monetary value across the app.
 *
 * Per Currency Rules (SRS §14): every stored monetary value carries its original
 * amount + currency, plus its USD equivalent at the exchange rate in effect when
 * it was entered/last edited (never "today's" rate — see ExchangeRateResolver).
 *
 * Per NFR-7: all monetary values are held at 4 decimal places internally to avoid
 * rounding drift across multi-step rollups (BOM -> product cost -> financial engine).
 * Rounding to a currency's display precision (e.g. 2 decimals) is a presentation-layer
 * concern and must never happen on the values stored/passed through this type.
 */
data class MoneyValue(
    val amount: BigDecimal,
    val currencyCode: String,
    val usdEquivalent: BigDecimal
) {

    init {
        require(currencyCode.isNotBlank()) {
            "MoneyValue must always carry a currency code — a currency-less amount is not permitted (Currency Rules §14)."
        }
        require(currencyCode == currencyCode.uppercase()) {
            "currencyCode must be uppercase ISO 4217 style (e.g. \"USD\", \"EGP\"), got \"$currencyCode\"."
        }
    }

    /** True when this value is already denominated in USD (amount == usdEquivalent by definition). */
    val isBaseCurrency: Boolean
        get() = currencyCode == "USD"

    /** Adds two MoneyValues. Both the native amount and the USD equivalent are summed independently — see class doc for why summing native amounts across different currencies is a caller error. */
    operator fun plus(other: MoneyValue): MoneyValue {
        require(currencyCode == other.currencyCode) {
            "Cannot add MoneyValue in \"$currencyCode\" to MoneyValue in \"${other.currencyCode}\" directly — " +
                "convert both to a common currency first (see CurrencyConverter in engine:currency-engine)."
        }
        return MoneyValue(
            amount = (amount + other.amount).atStoredScale(),
            currencyCode = currencyCode,
            usdEquivalent = (usdEquivalent + other.usdEquivalent).atStoredScale()
        )
    }

    operator fun times(factor: BigDecimal): MoneyValue = MoneyValue(
        amount = (amount * factor).atStoredScale(),
        currencyCode = currencyCode,
        usdEquivalent = (usdEquivalent * factor).atStoredScale()
    )

    companion object {
        /** Internal storage precision — 4 decimal places, per NFR-7. Never expose fewer decimals from this type; round only at the presentation layer. */
        const val STORED_SCALE = 4

        fun BigDecimal.atStoredScale(): BigDecimal = this.setScale(STORED_SCALE, RoundingMode.HALF_UP)

        /** A zero-value MoneyValue in the given currency, already USD-equivalent zero. Useful as a fold/reduce seed when summing collections. */
        fun zero(currencyCode: String): MoneyValue = MoneyValue(
            amount = BigDecimal.ZERO.atStoredScale(),
            currencyCode = currencyCode,
            usdEquivalent = BigDecimal.ZERO.atStoredScale()
        )

        /** Convenience constructor for values already in USD, where amount == usdEquivalent by definition. */
        fun usd(amount: BigDecimal): MoneyValue = MoneyValue(
            amount = amount.atStoredScale(),
            currencyCode = "USD",
            usdEquivalent = amount.atStoredScale()
        )
    }
}
