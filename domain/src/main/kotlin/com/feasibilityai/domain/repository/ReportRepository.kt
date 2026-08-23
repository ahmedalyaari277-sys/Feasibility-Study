package com.feasibilityai.domain.repository

import com.feasibilityai.domain.model.Report
import kotlinx.coroutines.flow.Flow

/**
 * Contract for Report persistence. Intentionally exposes no update/upsert — only
 * `create` (write-once) and reads/delete. A Report is an immutable snapshot (§18);
 * there is no legitimate reason for this interface to offer a way to mutate one, so
 * it simply doesn't — this is enforced by the interface shape itself, not a runtime check.
 */
interface ReportRepository {

    fun observeAllForProject(projectId: String): Flow<List<Report>>

    fun observeReport(reportId: String): Flow<Report?>

    suspend fun getReportOnce(reportId: String): Report?

    /** The only write operation. There is no `update`/`upsert` on this interface — see class doc. */
    suspend fun create(report: Report): Report

    /** Deleting a report removes the record and its exported file, but never alters other reports or live project data. */
    suspend fun deleteReport(reportId: String)
}
