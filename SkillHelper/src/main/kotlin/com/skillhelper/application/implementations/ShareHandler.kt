package com.skillhelper.application.implementations

import com.skillhelper.application.entities.ShareId
import com.skillhelper.application.entities.Username
import com.skillhelper.application.interfaces.IShareHandler
import com.skillhelper.application.throwables.ShareNotFoundException
import com.skillhelper.application.throwables.SkillNotFoundException
import com.skillhelper.application.throwables.UserNotFoundException
import com.skillhelper.application.entities.Share
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
    override fun addShare(share: Share) {
        if(!skillRepository.skillExists(share.skill)) throw SkillNotFoundException();
        if(!userRepository.userExists(share.forUser)) throw UserNotFoundException("User ${share.forUser.value} to not found.");
        if(!userRepository.userExists(share.fromUser)) throw UserNotFoundException("User ${share.fromUser.value} not found.");
        shareRepository.addShare(share);
    }

    override fun readShare(shareId: ShareId) {
        if(!shareRepository.shareExists(shareId)) throw ShareNotFoundException()
        shareRepository.readShare(shareId);
    }

    override fun deleteAllForUser(username: Username) {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        shareRepository.deleteAllForUser(username);
    }

    override fun deleteShare(shareId: ShareId) {
        shareRepository.deleteShare(shareId);
    }

    override fun getAll(username: Username): List<Share> {
        if(!userRepository.userExists(username)) throw UserNotFoundException(username.value);
        return shareRepository.getAllForUser(username);
    }
}