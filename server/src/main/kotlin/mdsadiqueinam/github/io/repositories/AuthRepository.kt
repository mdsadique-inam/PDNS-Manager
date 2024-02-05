package mdsadiqueinam.github.io.repositories

import models.User

interface AuthRepository<T> {
    /**
     * Attempts to authenticate the user for the current HTTP request. An exception
     * is raised when unable to do so
     */
    suspend fun authenticate(token: String): User

    /**
     * Attempts to authenticate the user for the current HTTP request and suppresses
     * exceptions raised by the [[authenticate]] method and returns a boolean
     */
    suspend fun check(token: String): Boolean

    /**
     * Attempt to verify user credentials and perform login
     */
    suspend fun attempt(uid: String, password: String): T

    /**
     * Login a user without any verification
     */
    suspend fun login(user: User): T
}