package com.admin.busmartappadmin2.service

import com.google.firestore.v1.StructuredQuery
import io.swagger.client.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // GET
// ----------------------------------- //
    @GET("Shops/GetShops")
    fun getShops(): Call<List<ShopVm>>

    @GET("Orders/GetOrdersActiveByUser/{userId}")
    fun getOrdersActiveByUser(@Path("userId") userId: String): Call<List<OrderVm>>

    @GET("Orders/GetOrdersNotActiveByUser/{userId}")
    fun getOrdersNotActiveByUser(@Path("userId") userId: String): Call<List<OrderVm>>

    @GET("Orders/GetOrdersByShop/{shopId}")
    fun getOrdersByShop(@Path("shopId") shopId: String): Call<List<OrderVm>>

    @GET("ShopItems/GetShopItems")
    fun getShopItems(): Call<List<ShopItemVm>>

    @GET("ShopItems/GetShopItemsByShop/{shopId}")
    fun getShopItemsByShop(@Path("shopId") shopId: String): Call<List<ShopItemVm>>

//    -------------------------------- //
    // POST

    @POST("ShopItems/PostShopItem")
    fun addShopItem(@Body shop: ShopItem): Call<ShopItem>

    @POST("Orders/PostOrder")
    fun addOrder(@Body order: StructuredQuery.Order): Call<Order>

    @POST("Shops/PostShop")
    fun addShop(@Body shop: Shop): Call<Shop>
}

