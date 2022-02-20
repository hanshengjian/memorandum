package com.hy.common.navigator

import com.hy.common.arouter.Route


interface BedoneNavigator {

    @Route(path = BEDONE_SERVICE_PATH)
    fun getBedoneService(): BedoneService

    companion object {
        const val EDIT_PAGE_PATH: String = "/bedone/edit_page"

        //service
        const val BEDONE_SERVICE_PATH: String = "/bedone/service"
    }
}