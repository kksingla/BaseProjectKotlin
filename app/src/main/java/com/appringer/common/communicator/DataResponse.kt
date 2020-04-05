package com.appringer.common.communicator

class DataResponse {
    /**
     * Callback interface for delivering parsed responses.
     */
    interface Listener<T> {
        /**
         * Called when a dataSource is received.
         */
        fun onResponse(dataSource: DataSource<T>)
    }
}