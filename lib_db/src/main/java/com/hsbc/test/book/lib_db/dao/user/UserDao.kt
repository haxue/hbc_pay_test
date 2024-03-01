package com.hsbc.test.book.lib_db.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hsbc.test.book.lib_db.dao.BaseDao
import com.hsbc.test.book.lib_db.entity.User

@Dao
interface UserDao : BaseDao<User> {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    override fun insert(todo: User)

    @Query("select * from tb_user where userNum = :userNum")
    fun getByNum(userNum: String): User?

    /**
     * 查询表里所有数据
     */
    @Query("select * from tb_user")
    fun getAllUser(): List<User>

    /**
     * 根据字段删除记录
     */
    @Query("delete from tb_user where userNum = :userNum")
    fun deleteByNum(userNum: String)


    /**
     * 修改指定用户的密码
     */
    @Query("update tb_user set userPwd =:userPwd where userNum =:userNum")
    fun updatePwd(userNum: String, userPwd: String)

    @Query("delete from tb_user")
    fun clearAll()
}