package com.skillhelper.application.implementations

import com.skillhelper.domain.entities.ShareId
import com.skillhelper.domain.entities.SkillId
import com.skillhelper.domain.entities.Username
import com.skillhelper.application.interfaces.IShareHandler
import com.skillhelper.api.mappers.toDomain
import com.skillhelper.api.mappers.toDto
import com.skillhelper.application.models.ShareCreationDto
import com.skillhelper.application.models.ShareDto
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
        if(!skillRepository.skillExists(SkillId(share.skillId))
            || !userRepository.userExists(Username(share.to))
            || !userRepository.userExists(Username(share.from))) return;
        shareRepository.addShare(share.toDomain());
    }

    override fun readShare(shareId: ShareId) {
        shareRepository.readShare(shareId);
    }

    override fun deleteAllForUser(username: Username) {
        shareRepository.deleteAllForUser(username);
    }

    override fun deleteShare(shareId: ShareId) {
        shareRepository.deleteShare(shareId);
    }

    override fun getAll(username: Username): List<ShareDto> {
        return shareRepository.getAllForUser(username).map{ dbo ->
            val profileImg = userRepository.getUserByName(dbo.fromUser)?.profile?.profileImage;
            val shareImg = skillRepository.getSkillById(dbo.skill)?.imageSrc

            dbo.toDto(profileImg, shareImg)
        }
    }
}