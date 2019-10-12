package com.example.desafio_dti.shared


class Operation<T> private constructor( val status: Status,
                                        val data: T?,
                                        val message: String?) {

    enum class Status {
        DONE, ERROR, RUNNING
    }

    companion object {

        fun <T> success( data: T): Operation<T> {
            return Operation<T>(
                Status.DONE,
                data,
                null
            )
        }

        fun <T> error(msg: String,  data: T?): Operation<T> {
            return Operation<T>(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> running( data: T? = null): Operation<T> {
            return Operation<T>(
                Status.RUNNING,
                data,
                null
            )
        }
    }
}