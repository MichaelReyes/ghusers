package com.technologies.ghusers.utils

import com.google.gson.Gson
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.extensions.fromJson
import com.technologies.ghusers.core.data.entity.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


val mockUsers: List<User> = Gson().fromJson(
    "[\n" +
            "  {\n" +
            "    \"login\": \"mojombo\",\n" +
            "    \"id\": 1,\n" +
            "    \"node_id\": \"MDQ6VXNlcjE=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/1?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/mojombo\",\n" +
            "    \"html_url\": \"https://github.com/mojombo\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/mojombo/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/mojombo/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/mojombo/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/mojombo/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/mojombo/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/mojombo/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/mojombo/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/mojombo/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/mojombo/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"defunkt\",\n" +
            "    \"id\": 2,\n" +
            "    \"node_id\": \"MDQ6VXNlcjI=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/2?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/defunkt\",\n" +
            "    \"html_url\": \"https://github.com/defunkt\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/defunkt/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/defunkt/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/defunkt/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/defunkt/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/defunkt/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/defunkt/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/defunkt/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/defunkt/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/defunkt/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"pjhyett\",\n" +
            "    \"id\": 3,\n" +
            "    \"node_id\": \"MDQ6VXNlcjM=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/3?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/pjhyett\",\n" +
            "    \"html_url\": \"https://github.com/pjhyett\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/pjhyett/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/pjhyett/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/pjhyett/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/pjhyett/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/pjhyett/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/pjhyett/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/pjhyett/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/pjhyett/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/pjhyett/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"wycats\",\n" +
            "    \"id\": 4,\n" +
            "    \"node_id\": \"MDQ6VXNlcjQ=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/4?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/wycats\",\n" +
            "    \"html_url\": \"https://github.com/wycats\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/wycats/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/wycats/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/wycats/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/wycats/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/wycats/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/wycats/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/wycats/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/wycats/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/wycats/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"ezmobius\",\n" +
            "    \"id\": 5,\n" +
            "    \"node_id\": \"MDQ6VXNlcjU=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/5?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/ezmobius\",\n" +
            "    \"html_url\": \"https://github.com/ezmobius\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/ezmobius/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/ezmobius/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/ezmobius/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/ezmobius/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/ezmobius/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/ezmobius/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/ezmobius/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/ezmobius/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/ezmobius/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"ivey\",\n" +
            "    \"id\": 6,\n" +
            "    \"node_id\": \"MDQ6VXNlcjY=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/6?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/ivey\",\n" +
            "    \"html_url\": \"https://github.com/ivey\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/ivey/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/ivey/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/ivey/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/ivey/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/ivey/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/ivey/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/ivey/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/ivey/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/ivey/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"evanphx\",\n" +
            "    \"id\": 7,\n" +
            "    \"node_id\": \"MDQ6VXNlcjc=\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/7?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/evanphx\",\n" +
            "    \"html_url\": \"https://github.com/evanphx\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/evanphx/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/evanphx/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/evanphx/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/evanphx/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/evanphx/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/evanphx/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/evanphx/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/evanphx/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/evanphx/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"vanpelt\",\n" +
            "    \"id\": 17,\n" +
            "    \"node_id\": \"MDQ6VXNlcjE3\",\n" +
            "    \"avatar_url\": \"https://avatars1.githubusercontent.com/u/17?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/vanpelt\",\n" +
            "    \"html_url\": \"https://github.com/vanpelt\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/vanpelt/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/vanpelt/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/vanpelt/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/vanpelt/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/vanpelt/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/vanpelt/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/vanpelt/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/vanpelt/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/vanpelt/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"wayneeseguin\",\n" +
            "    \"id\": 18,\n" +
            "    \"node_id\": \"MDQ6VXNlcjE4\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/18?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/wayneeseguin\",\n" +
            "    \"html_url\": \"https://github.com/wayneeseguin\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/wayneeseguin/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/wayneeseguin/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/wayneeseguin/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/wayneeseguin/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/wayneeseguin/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/wayneeseguin/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/wayneeseguin/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/wayneeseguin/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/wayneeseguin/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  },\n" +
            "  {\n" +
            "    \"login\": \"brynary\",\n" +
            "    \"id\": 19,\n" +
            "    \"node_id\": \"MDQ6VXNlcjE5\",\n" +
            "    \"avatar_url\": \"https://avatars0.githubusercontent.com/u/19?v=4\",\n" +
            "    \"gravatar_id\": \"\",\n" +
            "    \"url\": \"https://api.github.com/users/brynary\",\n" +
            "    \"html_url\": \"https://github.com/brynary\",\n" +
            "    \"followers_url\": \"https://api.github.com/users/brynary/followers\",\n" +
            "    \"following_url\": \"https://api.github.com/users/brynary/following{/other_user}\",\n" +
            "    \"gists_url\": \"https://api.github.com/users/brynary/gists{/gist_id}\",\n" +
            "    \"starred_url\": \"https://api.github.com/users/brynary/starred{/owner}{/repo}\",\n" +
            "    \"subscriptions_url\": \"https://api.github.com/users/brynary/subscriptions\",\n" +
            "    \"organizations_url\": \"https://api.github.com/users/brynary/orgs\",\n" +
            "    \"repos_url\": \"https://api.github.com/users/brynary/repos\",\n" +
            "    \"events_url\": \"https://api.github.com/users/brynary/events{/privacy}\",\n" +
            "    \"received_events_url\": \"https://api.github.com/users/brynary/received_events\",\n" +
            "    \"type\": \"User\",\n" +
            "    \"site_admin\": false\n" +
            "  }\n" +
            "]"
)

val userDetailsDefunkt: User = Gson().fromJson(
    "{\n" +
            "  \"login\": \"defunkt\",\n" +
            "  \"id\": 2,\n" +
            "  \"node_id\": \"MDQ6VXNlcjI=\",\n" +
            "  \"avatar_url\": \"https://avatars0.githubusercontent.com/u/2?v=4\",\n" +
            "  \"gravatar_id\": \"\",\n" +
            "  \"url\": \"https://api.github.com/users/defunkt\",\n" +
            "  \"html_url\": \"https://github.com/defunkt\",\n" +
            "  \"followers_url\": \"https://api.github.com/users/defunkt/followers\",\n" +
            "  \"following_url\": \"https://api.github.com/users/defunkt/following{/other_user}\",\n" +
            "  \"gists_url\": \"https://api.github.com/users/defunkt/gists{/gist_id}\",\n" +
            "  \"starred_url\": \"https://api.github.com/users/defunkt/starred{/owner}{/repo}\",\n" +
            "  \"subscriptions_url\": \"https://api.github.com/users/defunkt/subscriptions\",\n" +
            "  \"organizations_url\": \"https://api.github.com/users/defunkt/orgs\",\n" +
            "  \"repos_url\": \"https://api.github.com/users/defunkt/repos\",\n" +
            "  \"events_url\": \"https://api.github.com/users/defunkt/events{/privacy}\",\n" +
            "  \"received_events_url\": \"https://api.github.com/users/defunkt/received_events\",\n" +
            "  \"type\": \"User\",\n" +
            "  \"site_admin\": false,\n" +
            "  \"name\": \"Chris Wanstrath\",\n" +
            "  \"company\": null,\n" +
            "  \"blog\": \"http://chriswanstrath.com/\",\n" +
            "  \"location\": null,\n" +
            "  \"email\": null,\n" +
            "  \"hireable\": null,\n" +
            "  \"bio\": \"\uD83C\uDF54\",\n" +
            "  \"twitter_username\": null,\n" +
            "  \"public_repos\": 107,\n" +
            "  \"public_gists\": 273,\n" +
            "  \"followers\": 21104,\n" +
            "  \"following\": 210,\n" +
            "  \"created_at\": \"2007-10-20T05:24:19Z\",\n" +
            "  \"updated_at\": \"2019-11-01T21:56:00Z\"\n" +
            "}"
)

val noteSample: Note = Note(
    notes = "Sample notes",
    userId = 2
)

@OptIn(ExperimentalCoroutinesApi::class)
fun getUsers(since: Int): Flow<Resource<List<User>>> {
    return channelFlow {
        if (since != -1) {
            send(Resource.success(data = mockUsers.subList(since, since + 5)))
        } else {
            send(Resource.success(data = emptyList<User>()))
        }
    }
}

fun getUserData(userLogin: String): Flow<Resource<Pair<User, Note?>>> {
    return channelFlow {
        if (userLogin == "defunkt") {
            send(Resource.success(data = userDetailsDefunkt to noteSample))
        } else {
            send(Resource.error(data = null, message = "No user details found"))
        }
    }
}

fun getUserNote(userId: Int): Flow<Resource<Note>> {
    return channelFlow {
        if (userId == 2) {
            send(Resource.success(data = noteSample))
        } else {
            send(Resource.error(data = null, message = "No notes found"))
        }
    }
}