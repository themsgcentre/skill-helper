package com.skillhelper.application.interfaces

import com.skillhelper.application.entities.Skill
import com.skillhelper.application.entities.Username

/**
 * Strategy: encapsulates how a user may view a skill based on visibility rules.
 */
fun interface ISkillAccessPolicy {
    fun canView(viewer: Username, skill: Skill): Boolean
}
