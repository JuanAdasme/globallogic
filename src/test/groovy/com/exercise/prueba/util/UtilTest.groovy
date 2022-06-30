package com.exercise.prueba.util

import spock.lang.Specification

class UtilTest extends Specification {

    Utils utils = new Utils()

    def "MapUserToUserDTO"() {
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
