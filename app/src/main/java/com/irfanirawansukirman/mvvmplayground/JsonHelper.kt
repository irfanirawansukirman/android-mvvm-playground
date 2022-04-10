package com.irfanirawansukirman.mvvmplayground

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonHelper {

    fun run() {
        // serializing to string from object
        val test = Test("Irfan Irawan Sukirman, S.Kom.")
        val string = Json.encodeToString(test)
        println(string)

        // deserializing to object from string
        val obj = Json.decodeFromString<Test>(string)
        println(obj)

        // deserializing to list from string array
        // source: https://github.com/Kotlin/kotlinx.serialization/issues/1224#issuecomment-736289647
        val data = """[{"name": "Irfan"}, {"name": "Irawan"}, {"name": "Sukirman"}]"""
        val arr = Json.decodeFromString(ListSerializer(Test.serializer()), data)
        println(arr[0].name)

        // serializing to string from object array
        // source: old way https://stackoverflow.com/a/57669888
        // source: official issue https://github.com/Kotlin/kotlinx.serialization/issues/1224#issuecomment-736289647
        val arrString = Json.encodeToString(ListSerializer(Test.serializer()), arr)
        println(arrString)
    }

    @Serializable
    data class Test(val name: String)
}