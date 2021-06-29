package com.greensmod.FitFoster;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App instance;

    private AchievementsRoomDatabase databaseAchievements;
    private DaysRoomDatabase databaseDays;
    private PetInfoRoomDatabase databasePetInfo;
    private StatisticsRoomDatabase databaseStatistics;
    private WardrobeRoomDatabase databaseWardrobe;
    private WeeklyMissionsRoomDatabase databaseWeeklyMissions;

//    public static final Migration MIGRATION_DAYS_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase days_database) {
//            days_database.execSQL("ALTER TABLE days_table ADD COLUMN previous_steps INTEGER DEFAULT 0 NOT NULL");
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        databaseAchievements = Room.databaseBuilder(this, AchievementsRoomDatabase.class, "database_achievements")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        databaseDays = Room.databaseBuilder(this, DaysRoomDatabase.class, "database_days")
                .allowMainThreadQueries()
//                .addMigrations(App.MIGRATION_DAYS_1_2)
                .fallbackToDestructiveMigration()
                .build();
        databasePetInfo = Room.databaseBuilder(this, PetInfoRoomDatabase.class, "database_pet_info")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        databaseStatistics = Room.databaseBuilder(this, StatisticsRoomDatabase.class, "database_statistics")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        databaseWardrobe = Room.databaseBuilder(this, WardrobeRoomDatabase.class, "database_wardrobe")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        databaseWeeklyMissions = Room.databaseBuilder(this, WeeklyMissionsRoomDatabase.class, "database_weekly_missions")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AchievementsRoomDatabase getDatabaseAchievements() {
        return databaseAchievements;
    }

    public DaysRoomDatabase getDatabaseDays() {
        return databaseDays;
    }

    public PetInfoRoomDatabase getDatabasePetInfo() {
        return databasePetInfo;
    }

    public StatisticsRoomDatabase getDatabaseStatistics() {
        return databaseStatistics;
    }

    public WardrobeRoomDatabase getDatabaseWardrobe() {
        return databaseWardrobe;
    }

    public WeeklyMissionsRoomDatabase getDatabaseWeeklyMissions() {
        return databaseWeeklyMissions;
    }
}