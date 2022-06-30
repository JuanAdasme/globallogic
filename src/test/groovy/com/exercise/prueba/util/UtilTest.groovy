package com.exercise.prueba.util

import com.exercise.prueba.model.User
import spock.lang.Specification

class UtilTest extends Specification {

    Utils utils = new Utils()

    def "MapUserToUserDTO"() {
        given:
            def user = new User()
            user.setName("Django Freeman")
            user.setEmail("django@test.cl")

        when:
            def userDTO = utils.mapUserToUserDTO(user)

        then:
            user.getName() == userDTO.getName()
            user.getEmail() == userDTO.getEmail()
    }

    def "IsValidPassword"() {
        given:
            def validPassword = "a2asfGfdfdf4"

        when:
            def isValid = utils.isValidPassword(validPassword)

        then:
            isValid
    }

    def "IsInvalidPassword"() {
        given:
            def noUppercaseChar = "a2asffdfdf4"

        when:
            def isValid = utils.isValidPassword(noUppercaseChar)

        then:
            !isValid
    }
}
