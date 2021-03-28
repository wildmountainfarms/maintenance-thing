package me.retrodaredevil.maintenance.service

import io.leangen.graphql.annotations.GraphQLQuery

class SimpleGraphQLService {

    @GraphQLQuery
    fun queryMilli(): Int {
        return (System.currentTimeMillis() % 1000).toInt()
    }
}
