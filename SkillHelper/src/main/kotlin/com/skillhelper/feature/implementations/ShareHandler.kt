package com.skillhelper.feature.implementations

import com.skillhelper.feature.interfaces.IShareHandler
import com.skillhelper.feature.models.ShareCreationDto
import com.skillhelper.feature.models.ShareDto
import com.skillhelper.repository.interfaces.IShareRepository
import com.skillhelper.repository.interfaces.ISkillRepository
import com.skillhelper.repository.interfaces.IUserRepository
import org.springframework.stereotype.Service

@Service
class ShareHandler(
    val shareRepository: IShareRepository,
    val skillRepository: ISkillRepository,
    val userRepository: IUserRepository,
): IShareHandler {
    override fun addShare(share: ShareCreationDto) {
        shareRepository.addShare(share.toDbo());
    }

    override fun readShare(shareId: Long) {
        shareRepository.readShare(shareId);
    }

    override fun deleteAllForUser(username: String) {
        shareRepository.deleteAllForUser(username);
    }

    override fun deleteShare(shareId: Long) {
        shareRepository.deleteShare(shareId);
    }

    override fun getAll(username: String): List<ShareDto> {
        val shareDbos = shareRepository.getAllForUser(username);

        return shareDbos.map{ dbo ->
            val profileImg = userRepository.getUserByName(dbo.fromUser)?.profileImage;
            val shareImg = skillRepository.getSkillById(dbo.skill)?.imageSrc

            dbo.toDto(profileImg, shareImg)
        }
    }
}