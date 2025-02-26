package com.homanad.android.githubusers.ui.screens.util//package com.homanad.android.githubusers.ui.screens.home.fake
//
//import com.homanad.android.domain.common.RequestState
//import com.homanad.android.domain.models.GithubUser
//import com.homanad.android.domain.repositories.GithubRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import java.util.UUID
//
//class FakeGithubRepository : GithubRepository {
//
//    private val dummyUsers: List<GithubUser>
//        get() {
//            val username = UUID.randomUUID().toString()
//            return (0..140).map { createDummyUser(username) }
//        }
//
//    private fun createDummyUser(username: String) = GithubUser(username, "", "", "", 0, 0, "")
//
//    override suspend fun getUsers(perPage: Int, since: Int): List<GithubUser> {
//        return dummyUsers.subList(since, since + perPage)
//    }
//
//    override suspend fun getUser(username: String): Flow<RequestState<GithubUser>> = flow {
//        val data = dummyUsers.find { it.username == username }
//        data?.let {
//            emit(RequestState.Data(data))
//        }
//    }
//}