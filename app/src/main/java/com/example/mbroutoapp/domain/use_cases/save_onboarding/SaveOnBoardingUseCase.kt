package com.example.mbroutoapp.domain.use_cases.save_onboarding

import com.example.mbroutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed:Boolean){
            repository.saveOnBoardingState(completed)
    }
}