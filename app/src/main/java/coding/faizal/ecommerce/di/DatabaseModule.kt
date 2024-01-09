package coding.faizal.ecommerce.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val passphrase = SQLiteDatabase.getBytes("faizal".toCharArray())
    private val factory = SupportFactory(passphrase)


//    @Provides
//    @Singleton
//    fun roomDatabase(@ApplicationContext context : Context): MovieDatabase {
//        return Room.databaseBuilder(
//            context,
//            MovieDatabase::class.java,
//            "movie_db")
//            .fallbackToDestructiveMigration().openHelperFactory(factory).build()
//    }
//
//    @Provides
//    @Singleton
//    fun movieDao(movieDatabase : MovieDatabase): MovieDao {
//        return movieDatabase.movieDao()
//    }

}