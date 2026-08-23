package com.feasibilityai.domain.model

/**
 * An immutable generated report (Reporting Rules §18). Once created, a Report is
 * never edited — regenerating produces a new Report with a new id; this one's
 * snapshotJson, generatedAt, and filePath are permanent. There is deliberately no
 * "update" method or mutable field on this class.
 */
data class Report(
    val id: String,
    val projectId: String,
    val reportType: ReportType,
    val scenarioId: String,
    val language: ReportLanguage,
    val generatedAt: java.time.Instant,
    val snapshotJson: String, // full serialized copy of every value + exchange rates used, per §18
    val filePath: String,     // exported PDF or Excel file on disk
    val exportFormat: ReportExportFormat
)

enum class ReportType {
    EXECUTIVE_SUMMARY, FINANCIAL, TECHNICAL, FEASIBILITY_STUDY, BANK, INVESTOR, RISK, PRODUCT_COST
}

enum class ReportLanguage { ENGLISH, ARABIC }

enum class ReportExportFormat { PDF, EXCEL }
