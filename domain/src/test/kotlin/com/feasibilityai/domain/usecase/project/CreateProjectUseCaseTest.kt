package com.feasibilityai.domain.usecase.project

import com.feasibilityai.domain.model.Project
import com.feasibilityai.domain.model.ProjectMode
import com.feasibilityai.domain.model.ProjectTypeTemplate
import com.feasibilityai.domain.repository.ProjectRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreateProjectUseCaseTest {

    private lateinit var projectRepository: ProjectRepository
    private lateinit var useCase: CreateProjectUseCase

    private fun validInput(
        name: String = "Cairo Juice Factory",
        country: String = "Egypt",
        baseCurrencyCode: String = "usd",
        projectTypeId: String = ProjectTypeTemplate.FOOD_FACTORY,
        studyPeriodMonths: Int = 60,
        workingDaysPerYear: Int = 300,
        mode: ProjectMode = ProjectMode.MANUAL
    ) = CreateProjectUseCase.Input(
        name = name,
        country = country,
        baseCurrencyCode = baseCurrencyCode,
        projectTypeId = projectTypeId,
        studyPeriodMonths = studyPeriodMonths,
        workingDaysPerYear = workingDaysPerYear,
        mode = mode
    )

    @Before
    fun setUp() {
        projectRepository = mockk()
        useCase = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun `valid input creates and persists a project`() = runTest {
        val savedSlot = slot<Project>()
        coEvery { projectRepository.upsertProject(capture(savedSlot)) } answers { savedSlot.captured }

        val result = useCase(validInput())

        assertTrue("Expected success, got $result", result.isSuccess)
        val project = result.getOrThrow()
        assertEquals("Cairo Juice Factory", project.name)
        assertEquals("Egypt", project.country)
        assertEquals("USD", project.baseCurrencyCode) // uppercased by the use case
        assertEquals(ProjectTypeTemplate.FOOD_FACTORY, project.projectTypeId)
        assertEquals(ProjectMode.MANUAL, project.mode)

        coVerify(exactly = 1) { projectRepository.upsertProject(any()) }
    }

    @Test
    fun `blank name fails without persisting`() = runTest {
        val result = useCase(validInput(name = "   "))

        assertFalse(result.isSuccess)
        coVerify(exactly = 0) { projectRepository.upsertProject(any()) }
    }

    @Test
    fun `blank country fails without persisting`() = runTest {
        val result = useCase(validInput(country = ""))

        assertFalse(result.isSuccess)
        coVerify(exactly = 0) { projectRepository.upsertProject(any()) }
    }

    @Test
    fun `non-positive study period fails without persisting`() = runTest {
        val result = useCase(validInput(studyPeriodMonths = 0))

        assertFalse(result.isSuccess)
        coVerify(exactly = 0) { projectRepository.upsertProject(any()) }
    }

    @Test
    fun `working days out of range fails without persisting`() = runTest {
        val result = useCase(validInput(workingDaysPerYear = 400))

        assertFalse(result.isSuccess)
        coVerify(exactly = 0) { projectRepository.upsertProject(any()) }
    }

    @Test
    fun `optional blank fields are normalized to null`() = runTest {
        val savedSlot = slot<Project>()
        coEvery { projectRepository.upsertProject(capture(savedSlot)) } answers { savedSlot.captured }

        val input = validInput().copy(description = "   ", owner = "", location = null)
        val result = useCase(input)

        val project = result.getOrThrow()
        assertEquals(null, project.description)
        assertEquals(null, project.owner)
        assertEquals(null, project.location)
    }
}
