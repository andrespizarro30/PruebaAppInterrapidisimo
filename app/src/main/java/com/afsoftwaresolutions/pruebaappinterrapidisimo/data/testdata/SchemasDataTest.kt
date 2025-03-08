package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.testdata

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.SchemasResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.google.gson.Gson

object SchemasDataTest {

    val tableNames = listOf(
        "Users", "Orders", "Products", "Categories", "Customers",
        "Invoices", "Payments", "Transactions", "Shipping", "Vendors",
        "Suppliers", "Employees", "Departments", "Attendance", "Salaries",
        "Bonuses", "PerformanceReviews", "LeaveRequests", "Expenses", "Budgets",
        "Projects", "Tasks", "Meetings", "Clients", "Contracts", "AuditLogs",
        "Permissions", "Roles", "UserSessions", "Notifications", "Messages",
        "Comments", "Likes", "Posts", "Media", "Subscriptions", "Memberships",
        "Discounts", "Coupons", "Wishlists", "Reviews", "Ratings", "Inventory",
        "StockLevels", "Returns", "Refunds", "LoyaltyPoints", "GiftCards",
        "Announcements", "Reports", "Settings"
    )

    fun getSchemasData() : List<SchemasResponse>{
        val schemaDataList = tableNames.map { SchemasResponse(it) }
        return schemaDataList
    }
}