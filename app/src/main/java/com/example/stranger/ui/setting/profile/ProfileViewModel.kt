package com.example.stranger.ui.setting.fragment.profile

import androidx.lifecycle.ViewModel
import com.example.stranger.missionProFile
import com.example.stranger.mode.ProFile
import com.example.stranger.repository.Repository

class ProfileViewModel : ViewModel() {
    private val repository : Repository = Repository()
    fun getUserLogin()= repository.getUserLogin()
    fun profile(uid:String , proFileMission: proFileMission){
        repository.getProFile(uid, object : missionProFile{
            override fun succsess(proFile: ProFile) {
                proFileMission.succsess(proFile)
            }

        })
    }
}
interface proFileMission{
    fun succsess(proFile: ProFile)
}