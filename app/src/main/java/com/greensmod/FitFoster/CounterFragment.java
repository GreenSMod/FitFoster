package com.greensmod.FitFoster;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.List;


public class CounterFragment extends Fragment {

    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

//        // This method sets up our custom logger, which will print all log messages to the device
//        // screen, as well as to adb logcat.
//        initializeLogging(view);

        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA)
                        .addDataType(DataType.TYPE_MOVE_MINUTES)
                        .build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getActivity()), fitnessOptions)) {
            Intent intent = new Intent(getActivity(), GoogleAuth.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            subscribe();
        }

        update(view, true, true, true, true, true);

        Button updateButton = view.findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(view, true, true, true, true, true);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribe();
            }
        }
    }

//    /** Initializes a custom log class that outputs both to in-app targets and logcat. */
//    private void initializeLogging(View view) {
//        // Wraps Android's native log framework.
//        LogWrapper logWrapper = new LogWrapper();
//        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
//        Log.setLogNode(logWrapper);
//        // Filter strips out everything except the message text.
//        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
//        logWrapper.setNext(msgFilter);
//        // On screen logging via a customized TextView.
//        LogView logView = view.findViewById(R.id.sample_logview);
//
//        // Fixing this lint error adds logic without benefit.
//        // noinspection AndroidLintDeprecation
////        logView.setTextAppearance(R.style.Log);
//
//        logView.setBackgroundColor(Color.WHITE);
//        msgFilter.setNext(logView);
//        Log.i(TAG, "Ready");
//    }

    /**
     * Records step data by requesting a subscription to background step data.
     */
    public void subscribe() {
        Fitness.getRecordingClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
////                                    Log.i(TAG, "Successfully subscribed!");
//                                } else {
////                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
//                                }
                            }
                        });
    }

    @SuppressLint("SetTextI18n")
    public void update(View view, boolean ifSteps, boolean ifHeartPoints,
                       boolean ifCalories, boolean ifDistance, boolean ifTime) {
        TextView steps = view.findViewById(R.id.steps);
        ProgressBar steps_progress = view.findViewById(R.id.progressSteps);
        TextView heart_points = view.findViewById(R.id.heartPoints);
        ProgressBar heart_points_progress = view.findViewById(R.id.progressHeartPoints);
        TextView calories = view.findViewById(R.id.calories);
        TextView distance = view.findViewById(R.id.distance);
        TextView time = view.findViewById(R.id.time);

        TextView weekly_missions_time = view.findViewById(R.id.weekly_missions_time);
        TextView daily_missions_time = view.findViewById(R.id.daily_missions_time);
        TextView daily_mission_1 = view.findViewById(R.id.daily_mission_1);
        ProgressBar daily_mission_1_progress = view.findViewById(R.id.daily_mission_1_progress);
        TextView daily_mission_1_progress_text = view.findViewById(R.id.daily_mission_1_progress_text);
        TextView daily_mission_2 = view.findViewById(R.id.daily_mission_2);
        ProgressBar daily_mission_2_progress = view.findViewById(R.id.daily_mission_2_progress);
        TextView daily_mission_2_progress_text = view.findViewById(R.id.daily_mission_2_progress_text);
        TextView daily_mission_3 = view.findViewById(R.id.daily_mission_3);
        ProgressBar daily_mission_3_progress = view.findViewById(R.id.daily_mission_3_progress);
        TextView daily_mission_3_progress_text = view.findViewById(R.id.daily_mission_3_progress_text);
        TextView daily_mission_4 = view.findViewById(R.id.daily_mission_4);
        ProgressBar daily_mission_4_progress = view.findViewById(R.id.daily_mission_4_progress);
        TextView daily_mission_4_progress_text = view.findViewById(R.id.daily_mission_4_progress_text);

        DaysRoomDatabase dbDays = App.getInstance().getDatabaseDays();
        DaysDao daysDao = dbDays.daysDao();

        PetInfoRoomDatabase dbPetInfo = App.getInstance().getDatabasePetInfo();
        PetInfoDao petInfoDao = dbPetInfo.petInfoDao();
        List<PetInfo> petInfoGetAll = petInfoDao.getAll();
        if (petInfoGetAll.size() == 0) {
            PetInfo petInfoF = new PetInfo();
            petInfoF.id = 1;
            petInfoF.texture_id = 1;
            petInfoDao.insert(petInfoF);
        }
        PetInfo pet_info_db = petInfoDao.getById(1);

        StatisticsRoomDatabase dbStatistics = App.getInstance().getDatabaseStatistics();
        StatisticsDao statisticsDao = dbStatistics.statisticsDao();
        String[][] statistics_list = {
                {getResources().getString(R.string.statistics_1_label), getResources().getString(R.string.statistics_1_description), "1"},
                {getResources().getString(R.string.statistics_2_label), getResources().getString(R.string.statistics_2_description), "-1"},
                {getResources().getString(R.string.statistics_3_label), getResources().getString(R.string.statistics_3_description), "-1"},
                {getResources().getString(R.string.statistics_4_label), getResources().getString(R.string.statistics_4_description), "-1"},
                {getResources().getString(R.string.statistics_5_label), getResources().getString(R.string.statistics_5_description), "-1"},
                {getResources().getString(R.string.statistics_6_label), getResources().getString(R.string.statistics_6_description), "1"},
                {getResources().getString(R.string.statistics_7_label), getResources().getString(R.string.statistics_7_description), "1"},
                {getResources().getString(R.string.statistics_8_label), getResources().getString(R.string.statistics_8_description), "1"},
                {getResources().getString(R.string.statistics_9_label), getResources().getString(R.string.statistics_9_description), "1"},
                {getResources().getString(R.string.statistics_10_label), getResources().getString(R.string.statistics_10_description), "-1"},
                {getResources().getString(R.string.statistics_11_label), getResources().getString(R.string.statistics_11_description), "-1"}
        };
        for (String[] item : statistics_list) {
            Statistics statistics_i = statisticsDao.getByLabel(item[0]);
            if (statistics_i == null) {
                Statistics statisticsF = new Statistics();
                statisticsF.label = item[0];
                statisticsF.description = item[1];
                statisticsF.type_id = Integer.parseInt(item[2]);
                statisticsDao.insert(statisticsF);
            } else {
                if (!statistics_i.description.equals(item[1])) {
                    statistics_i.description = item[1];
                    statisticsDao.update(statistics_i);
                }
                if (statistics_i.type_id != Integer.parseInt(item[2])) {
                    statistics_i.type_id = Integer.parseInt(item[2]);
                    statisticsDao.update(statistics_i);
                }
            }
        }

        AchievementsRoomDatabase dbAchievements = App.getInstance().getDatabaseAchievements();
        AchievementsDao achievementsDao = dbAchievements.achievementsDao();
        String[][] achievements_list = {
                {getResources().getString(R.string.achievement_1_label), getResources().getString(R.string.achievement_1_name), getResources().getString(R.string.achievement_1_description), "1"},
                {getResources().getString(R.string.achievement_2_label), getResources().getString(R.string.achievement_2_name), getResources().getString(R.string.achievement_2_description), "1"},
                {getResources().getString(R.string.achievement_3_label), getResources().getString(R.string.achievement_3_name), getResources().getString(R.string.achievement_3_description), "1"},
                {getResources().getString(R.string.achievement_4_label), getResources().getString(R.string.achievement_4_name), getResources().getString(R.string.achievement_4_description), "1"},
                {getResources().getString(R.string.achievement_5_label), getResources().getString(R.string.achievement_5_name), getResources().getString(R.string.achievement_5_description), "1"},
                {getResources().getString(R.string.achievement_6_label), getResources().getString(R.string.achievement_6_name), getResources().getString(R.string.achievement_6_description), "2"},
                {getResources().getString(R.string.achievement_7_label), getResources().getString(R.string.achievement_7_name), getResources().getString(R.string.achievement_7_description), "-1"},
                {getResources().getString(R.string.achievement_8_label), getResources().getString(R.string.achievement_8_name), getResources().getString(R.string.achievement_8_description), "-1"},
                {getResources().getString(R.string.achievement_9_label), getResources().getString(R.string.achievement_9_name), getResources().getString(R.string.achievement_9_description), "-1"},
                {getResources().getString(R.string.achievement_10_label), getResources().getString(R.string.achievement_10_name), getResources().getString(R.string.achievement_10_description), "2"},
                {getResources().getString(R.string.achievement_11_label), getResources().getString(R.string.achievement_11_name), getResources().getString(R.string.achievement_11_description), "1"},
                {getResources().getString(R.string.achievement_12_label), getResources().getString(R.string.achievement_12_name), getResources().getString(R.string.achievement_12_description), "1"},
                {getResources().getString(R.string.achievement_13_label), getResources().getString(R.string.achievement_13_name), getResources().getString(R.string.achievement_13_description), "1"},
                {getResources().getString(R.string.achievement_14_label), getResources().getString(R.string.achievement_14_name), getResources().getString(R.string.achievement_14_description), "1"},
                {getResources().getString(R.string.achievement_15_label), getResources().getString(R.string.achievement_15_name), getResources().getString(R.string.achievement_15_description), "2"}
        };
        for (String[] value : achievements_list) {
            Achievements achievements_i = achievementsDao.getByLabel(value[0]);
            if (achievements_i == null) {
                Achievements achievementsF = new Achievements();
                achievementsF.label = value[0];
                achievementsF.name = value[1];
                achievementsF.description = value[2];
                achievementsF.type_id = Integer.parseInt(value[3]);
                achievementsDao.insert(achievementsF);
            } else {
                if (!achievements_i.name.equals(value[1])) {
                    achievements_i.name = value[1];
                    achievementsDao.update(achievements_i);
                }
                if (!achievements_i.description.equals(value[2])) {
                    achievements_i.description = value[2];
                    achievementsDao.update(achievements_i);
                }
                if (achievements_i.type_id != Integer.parseInt(value[3])) {
                    achievements_i.type_id = Integer.parseInt(value[3]);
                    achievementsDao.update(achievements_i);
                }
            }
        }

        WardrobeRoomDatabase dbWardrobe = App.getInstance().getDatabaseWardrobe();
        WardrobeDao wardrobeDao = dbWardrobe.wardrobeDao();
        String[][] wardrobe_list = {
                {"1", "1", getResources().getString(R.string.wardrobe_texture_1), "0", "0", "1", "0", String.valueOf(R.drawable.texture_1)},
                {"2", "1", getResources().getString(R.string.wardrobe_texture_2), "0", "0", "1", "0", String.valueOf(R.drawable.texture_2)},
                {"497", "7", getResources().getString(R.string.wardrobe_body_1), "0", "0", "1", "0", String.valueOf(R.drawable.body_1)},
                {"498", "7", getResources().getString(R.string.wardrobe_body_2), "0", "0", "1", "0", String.valueOf(R.drawable.body_2)},
                {"499", "7", getResources().getString(R.string.wardrobe_body_3), "0", "0", "1", "0", String.valueOf(R.drawable.body_3)},
                {"500", "7", getResources().getString(R.string.wardrobe_body_4), "0", "0", "1", "0", String.valueOf(R.drawable.body_4)},
                {"501", "7", getResources().getString(R.string.wardrobe_body_5), "0", "0", "1", "0", String.valueOf(R.drawable.body_5)},
        };
        for (String[] strings : wardrobe_list) {
            Wardrobe wardrobe_i = wardrobeDao.getById(Integer.parseInt(strings[0]));
            if (wardrobe_i == null) {
                Wardrobe wardrobeF = new Wardrobe();
                wardrobeF.id = Integer.parseInt(strings[0]);
                wardrobeF.type_id = Integer.parseInt(strings[1]);
                wardrobeF.name = strings[2];
                wardrobeF.points_type_id = Integer.parseInt(strings[3]);
                wardrobeF.cost = Integer.parseInt(strings[4]);
                wardrobeF.bought_type_id = Integer.parseInt(strings[5]);
                wardrobeF.getting_type_id = Integer.parseInt(strings[6]);
                wardrobeF.resource_id = Integer.parseInt(strings[7]);
                wardrobeDao.insert(wardrobeF);
            } else {
                if (wardrobe_i.type_id != Integer.parseInt(strings[1])) {
                    wardrobe_i.type_id = Integer.parseInt(strings[1]);
                    wardrobeDao.update(wardrobe_i);
                }
                if (!wardrobe_i.name.equals(strings[2])) {
                    wardrobe_i.name = strings[2];
                    wardrobeDao.update(wardrobe_i);
                }
                if (wardrobe_i.points_type_id != Integer.parseInt(strings[3])) {
                    wardrobe_i.points_type_id = Integer.parseInt(strings[3]);
                    wardrobeDao.update(wardrobe_i);
                }
                if (wardrobe_i.cost != Integer.parseInt(strings[4])) {
                    wardrobe_i.cost = Integer.parseInt(strings[4]);
                    wardrobeDao.update(wardrobe_i);
                }
                if (wardrobe_i.bought_type_id != Integer.parseInt(strings[5])) {
                    wardrobe_i.bought_type_id = Integer.parseInt(strings[5]);
                    wardrobeDao.update(wardrobe_i);
                }
                if (wardrobe_i.getting_type_id != Integer.parseInt(strings[6])) {
                    wardrobe_i.getting_type_id = Integer.parseInt(strings[6]);
                    wardrobeDao.update(wardrobe_i);
                }
                if (wardrobe_i.resource_id != Integer.parseInt(strings[7])) {
                    wardrobe_i.resource_id = Integer.parseInt(strings[7]);
                    wardrobeDao.update(wardrobe_i);
                }
            }
        }

        WeeklyMissionsRoomDatabase dbWeeklyMissions = App.getInstance().getDatabaseWeeklyMissions();
        WeeklyMissionsDao weeklyMissionsDao = dbWeeklyMissions.weeklyMissionsDao();

        int date_day = Calendar.getInstance().get(Calendar.DATE);
        int date_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int date_year = Calendar.getInstance().get(Calendar.YEAR);
        int date_into_database = date_year * 10000 + date_month * 100 + date_day;
        int date_hour_of_day = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int date_day_of_week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int day_of_year = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        int hour;
        if (date_hour_of_day == 23) {
            hour = 0;
            daily_missions_time.setText(getResources().getString(R.string.text_less_than_hour));
        } else {
            hour = (23 - date_hour_of_day);
            daily_missions_time.setText(hour + getResources().getString(R.string.text_hour));
        }

        if (date_day_of_week == 1) {
            if (hour == 0) {
                weekly_missions_time.setText(getResources().getString(R.string.text_less_than_hour));
            } else {
                weekly_missions_time.setText(hour + getResources().getString(R.string.text_hour));
            }
        } else {
            if (hour == 0) {
                weekly_missions_time.setText((8 - date_day_of_week) + getResources().getString(R.string.text_day));
            } else {
                weekly_missions_time.setText((8 - date_day_of_week) + getResources().getString(R.string.text_day) + " " + hour + getResources().getString(R.string.text_hour));
            }
        }

        Days days_search_date = daysDao.getByDate(date_into_database);
        Days daysF;
        if (days_search_date == null) {
            daysF = new Days();
            daysF.completed_daily_missions = 1111;
            daysF.date = date_into_database;
            daysDao.insert(daysF);
        }

        List<WeeklyMissions> weeklyMissionsGetAll = weeklyMissionsDao.getAll();
        WeeklyMissions weekly_missions_search_date = weeklyMissionsDao.getByDate(date_into_database);
        if (weeklyMissionsGetAll.size() == 0) {
            WeeklyMissions weeklyMissionsF;
            weeklyMissionsF = new WeeklyMissions();
            weeklyMissionsF.date = date_into_database;
            weeklyMissionsF.day_of_year = day_of_year;
            weeklyMissionsF.completed_weekly_missions = 111;
            weeklyMissionsDao.insert(weeklyMissionsF);
        } else if (date_day_of_week == 2) {
            if (weekly_missions_search_date == null) {
                WeeklyMissions weeklyMissionsF;
                weeklyMissionsF = new WeeklyMissions();
                weeklyMissionsF.date = date_into_database;
                weeklyMissionsF.day_of_year = day_of_year;
                weeklyMissionsF.completed_weekly_missions = 111;
                weeklyMissionsDao.insert(weeklyMissionsF);
            }
        } else {
            if (weekly_missions_search_date == null) {
                WeeklyMissions weeklyMissionsPrevious = weeklyMissionsGetAll.get(weeklyMissionsGetAll.size() - 1);
                int date_last = weeklyMissionsPrevious.date;
                int day_of_year_last = weeklyMissionsPrevious.day_of_year;

                int year = date_into_database / 10000;
                int year_last = date_last / 10000;

                if (year == year_last && day_of_year - day_of_year_last < 7) {
                    weeklyMissionsPrevious.date = date_into_database;
                    weeklyMissionsPrevious.day_of_year = day_of_year;
                    weeklyMissionsDao.update(weeklyMissionsPrevious);
                } else if (year == year_last && day_of_year - day_of_year_last >= 7) {
                    WeeklyMissions weeklyMissionsF;
                    weeklyMissionsF = new WeeklyMissions();
                    weeklyMissionsF.date = date_into_database;
                    weeklyMissionsF.day_of_year = day_of_year;
                    weeklyMissionsF.completed_weekly_missions = 111;
                    weeklyMissionsDao.insert(weeklyMissionsF);
                } else if (year != year_last) {
                    int leapPlus = 0;
                    if (((year_last % 4 == 0) && (year_last % 100 != 0)) || (year_last % 400 == 0)) {
                        leapPlus = 1;
                    }
                    if (day_of_year + 365 + leapPlus - day_of_year_last < 7) {
                        weeklyMissionsPrevious.date = date_into_database;
                        weeklyMissionsPrevious.day_of_year = day_of_year;
                        weeklyMissionsDao.update(weeklyMissionsPrevious);
                    } else if (day_of_year + 365 + leapPlus - day_of_year_last >= 7) {
                        WeeklyMissions weeklyMissionsF;
                        weeklyMissionsF = new WeeklyMissions();
                        weeklyMissionsF.date = date_into_database;
                        weeklyMissionsF.day_of_year = day_of_year;
                        weeklyMissionsF.completed_weekly_missions = 111;
                        weeklyMissionsDao.insert(weeklyMissionsF);
                    }
                }
            }
        }
        WeeklyMissions weekly_missions_db = weeklyMissionsDao.getByDate(date_into_database);

        check_weekly_missions(view, true, true, true);

        if (ifSteps) {
            Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                    .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                    .addOnSuccessListener(
                            new OnSuccessListener<DataSet>() {
                                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                                @Override
                                public void onSuccess(DataSet dataSet) {
                                    long total =
                                            dataSet.isEmpty()
                                                    ? 0
                                                    : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                                    steps.setText(total + "");
                                    if (total > 200) {
                                        steps_progress.setProgress((int) total);
                                    }
                                    Days days_search_date = daysDao.getByDate(date_into_database);
                                    days_search_date.steps = (int) total;
                                    daily_mission_2_progress.setProgress((int) total);
                                    if ((int) total >= 5000) {
                                        daily_mission_2.setTextColor(getResources().getColor(R.color.green_success));
                                        if (((days_search_date.completed_daily_missions / 100) % 10) == 1) {
                                            days_search_date.completed_daily_missions += 100;
                                            pet_info_db.events_points++;
                                            petInfoDao.update(pet_info_db);
                                            Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_7_label));
                                            statistics_i.value++;
                                            statisticsDao.update(statistics_i);
                                            weekly_missions_db.weekly_completed_daily_missions++;
                                            weeklyMissionsDao.update(weekly_missions_db);
                                            check_weekly_missions(view, true, true, true);
                                        }
                                        Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_1_label));
                                        if ((int) total >= 20000 && achievements_i.completed_type_id != 1) {
                                            achievements_i.completed_type_id = 1;
                                            achievementsDao.update(achievements_i);
                                        }
                                    } else {
                                        daily_mission_2_progress_text.setText(total + "/5000");
                                    }

                                    if (days_search_date.previous_steps != total && total != 0) {
                                        pet_info_db.steps_points += ((int) total - days_search_date.previous_steps);
                                        petInfoDao.update(pet_info_db);
                                        Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_1_label));
                                        statistics_i.value += ((int) total - days_search_date.previous_steps);
                                        statisticsDao.update(statistics_i);
                                        Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_6_label));
                                        if (statistics_i.value >= 1000000 && achievements_i.completed_type_id != 1) {
                                            achievements_i.completed_type_id = 1;
                                            achievementsDao.update(achievements_i);
                                        }
                                        days_search_date.previous_steps = (int) total;
                                    }
                                    daysDao.update(days_search_date);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "There was a problem getting the step count.", e);
                                }
                            });
        }

        if (ifHeartPoints) {
            Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                    .readDailyTotal(DataType.TYPE_HEART_POINTS)
                    .addOnSuccessListener(
                            new OnSuccessListener<DataSet>() {
                                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                                @Override
                                public void onSuccess(DataSet dataSet) {
                                    int total =
                                            dataSet.isEmpty()
                                                    ? 0
                                                    : (int) dataSet.getDataPoints().get(0).getValue(Field.FIELD_INTENSITY).asFloat();
                                    heart_points.setText(total + "");
                                    if (total > 1) {
                                        heart_points_progress.setProgress(total);
                                    }
                                    Days days_search_date = daysDao.getByDate(date_into_database);
                                    days_search_date.heart_points = total;

                                    daily_mission_4_progress.setProgress(total);
                                    if (total >= 25) {
                                        daily_mission_4.setTextColor(getResources().getColor(R.color.green_success));
                                        if ((days_search_date.completed_daily_missions % 10) == 1) {
                                            days_search_date.completed_daily_missions = days_search_date.completed_daily_missions + 1;
                                            pet_info_db.events_points = pet_info_db.events_points + 2;
                                            petInfoDao.update(pet_info_db);
                                            Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_7_label));
                                            statistics_i.value++;
                                            statisticsDao.update(statistics_i);
                                            weekly_missions_db.weekly_completed_daily_missions = weekly_missions_db.weekly_completed_daily_missions + 1;
                                            weeklyMissionsDao.update(weekly_missions_db);
                                            check_weekly_missions(view, true, true, true);
                                        }
                                        Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_2_label));
                                        if (total >= 250 && achievements_i.completed_type_id != 1) {
                                            achievements_i.completed_type_id = 1;
                                            achievementsDao.update(achievements_i);
                                        }
                                    } else {
                                        daily_mission_4_progress_text.setText(total + "/25");
                                    }
                                    daysDao.update(days_search_date);

                                    if (weekly_missions_db.previous_heart_points != total && total != 0) {
                                        weekly_missions_db.weekly_heart_points = weekly_missions_db.weekly_heart_points + (total - weekly_missions_db.previous_heart_points);
                                        weekly_missions_db.previous_heart_points = total;
                                        weeklyMissionsDao.update(weekly_missions_db);
                                        check_weekly_missions(view, true, true, true);
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "There was a problem getting the step count.", e);
                                }
                            });
        }

        if (ifCalories) {
            Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                    .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                    .addOnSuccessListener(
                            new OnSuccessListener<DataSet>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onSuccess(DataSet dataSet) {
                                    int total =
                                            dataSet.isEmpty()
                                                    ? 0
                                                    : (int) dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                                    calories.setText(total + "");
                                    Days days_search_date = daysDao.getByDate(date_into_database);
                                    days_search_date.calories = total;
                                    daysDao.update(days_search_date);
                                    Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_3_label));
                                    if (total >= 3000 && achievements_i.completed_type_id != 1) {
                                        achievements_i.completed_type_id = 1;
                                        achievementsDao.update(achievements_i);
                                    }
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "There was a problem getting the step count.", e);
                                }
                            });
        }

        if (ifDistance) {
            Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                    .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                    .addOnSuccessListener(
                            new OnSuccessListener<DataSet>() {
                                @SuppressLint({"SetTextI18n", "DefaultLocale", "ResourceAsColor"})
                                @Override
                                public void onSuccess(DataSet dataSet) {
                                    float total =
                                            dataSet.isEmpty()
                                                    ? 0
                                                    : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                                    distance.setText(String.format("%.2f", total / 1000));

                                    DecimalFormat twoDForm = new DecimalFormat("0.00");
                                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                                    dfs.setDecimalSeparator('.');
                                    twoDForm.setDecimalFormatSymbols(dfs);
                                    Days days_search_date = daysDao.getByDate(date_into_database);
                                    days_search_date.distance = Float.parseFloat(twoDForm.format(total / 1000));

                                    daily_mission_1_progress.setProgress((int) (total / 10));
                                    if (total / 1000 >= 3) {
                                        daily_mission_1.setTextColor(getResources().getColor(R.color.green_success));
                                        if (((days_search_date.completed_daily_missions / 1000) % 10) == 1) {
                                            days_search_date.completed_daily_missions = days_search_date.completed_daily_missions + 1000;
                                            pet_info_db.events_points = pet_info_db.events_points + 1;
                                            petInfoDao.update(pet_info_db);
                                            Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_7_label));
                                            statistics_i.value++;
                                            statisticsDao.update(statistics_i);
                                            weekly_missions_db.weekly_completed_daily_missions = weekly_missions_db.weekly_completed_daily_missions + 1;
                                            weeklyMissionsDao.update(weekly_missions_db);
                                            check_weekly_missions(view, true, true, true);
                                        }
                                        Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_4_label));
                                        if (total / 1000 >= 15 && achievements_i.completed_type_id != 1) {
                                            achievements_i.completed_type_id = 1;
                                            achievementsDao.update(achievements_i);
                                        }
                                    } else {
                                        daily_mission_1_progress_text.setText(String.format("%.2f", total / 1000) + "/3");
                                    }
                                    daysDao.update(days_search_date);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                    System.out.println(e);
//                                Log.w(TAG, "There was a problem getting the step count.", e);
                                }
                            });
        }

        if (ifTime) {
            Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                    .readDailyTotal(DataType.TYPE_MOVE_MINUTES)
                    .addOnSuccessListener(
                            new OnSuccessListener<DataSet>() {
                                @SuppressLint({"SetTextI18n", "ResourceAsColor"})
                                @Override
                                public void onSuccess(DataSet dataSet) {
                                    long total =
                                            dataSet.isEmpty()
                                                    ? 0
                                                    : (long) dataSet.getDataPoints().get(0).getValue(Field.FIELD_DURATION).asInt();
                                    time.setText(total + "");
                                    Days days_search_date = daysDao.getByDate(date_into_database);
                                    days_search_date.time = (int) total;

                                    daily_mission_3_progress.setProgress((int) total);
                                    if ((int) total >= 90) {
                                        daily_mission_3.setTextColor(getResources().getColor(R.color.green_success));
                                        if (((days_search_date.completed_daily_missions / 10) % 10) == 1) {
                                            days_search_date.completed_daily_missions = days_search_date.completed_daily_missions + 10;
                                            pet_info_db.events_points = pet_info_db.events_points + 1;
                                            petInfoDao.update(pet_info_db);
                                            Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_7_label));
                                            statistics_i.value++;
                                            statisticsDao.update(statistics_i);
                                            weekly_missions_db.weekly_completed_daily_missions = weekly_missions_db.weekly_completed_daily_missions + 1;
                                            weeklyMissionsDao.update(weekly_missions_db);
                                            check_weekly_missions(view, true, true, true);
                                        }
                                        Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_5_label));
                                        if ((int) total >= 300 && achievements_i.completed_type_id != 1) {
                                            achievements_i.completed_type_id = 1;
                                            achievementsDao.update(achievements_i);
                                        }
                                    } else {
                                        daily_mission_3_progress_text.setText(total + "/90");
                                    }
                                    daysDao.update(days_search_date);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "There was a problem getting the step count.", e);
                                }
                            });
        }
    }

    @SuppressLint("SetTextI18n")
    public void check_weekly_missions(View view, boolean ifMission1, boolean ifMission2, boolean ifMission3) {
        TextView weekly_mission_1 = view.findViewById(R.id.weekly_mission_1);
        ProgressBar weekly_mission_1_progress = view.findViewById(R.id.weekly_mission_1_progress);
        TextView weekly_mission_1_progress_text = view.findViewById(R.id.weekly_mission_1_progress_text);
        TextView weekly_mission_2 = view.findViewById(R.id.weekly_mission_2);
        ProgressBar weekly_mission_2_progress = view.findViewById(R.id.weekly_mission_2_progress);
        TextView weekly_mission_2_progress_text = view.findViewById(R.id.weekly_mission_2_progress_text);
        TextView weekly_mission_3 = view.findViewById(R.id.weekly_mission_3);
        ProgressBar weekly_mission_3_progress = view.findViewById(R.id.weekly_mission_3_progress);
        TextView weekly_mission_3_progress_text = view.findViewById(R.id.weekly_mission_3_progress_text);

        WeeklyMissionsRoomDatabase dbWeeklyMissions = App.getInstance().getDatabaseWeeklyMissions();
        WeeklyMissionsDao weeklyMissionsDao = dbWeeklyMissions.weeklyMissionsDao();
        List<WeeklyMissions> weeklyMissionsGetAll = weeklyMissionsDao.getAll();
        WeeklyMissions weekly_missions_db = weeklyMissionsGetAll.get(weeklyMissionsGetAll.size() - 1);

        PetInfoRoomDatabase dbPetInfo = App.getInstance().getDatabasePetInfo();
        PetInfoDao petInfoDao = dbPetInfo.petInfoDao();
        PetInfo pet_info_db = petInfoDao.getById(1);

        StatisticsRoomDatabase dbStatistics = App.getInstance().getDatabaseStatistics();
        StatisticsDao statisticsDao = dbStatistics.statisticsDao();

        AchievementsRoomDatabase dbAchievements = App.getInstance().getDatabaseAchievements();
        AchievementsDao achievementsDao = dbAchievements.achievementsDao();

        if (weeklyMissionsGetAll.size() > 1) {
            WeeklyMissions previous_weekly_missions_db = weeklyMissionsGetAll.get(weeklyMissionsGetAll.size() - 2);
            if (previous_weekly_missions_db.previous_heart_points < 250) {
                Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_10_label));
                statistics_i.value = 0;
                statisticsDao.update(statistics_i);
            }
            if (previous_weekly_missions_db.completed_weekly_missions != 222) {
                Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_11_label));
                statistics_i.value = 0;
                statisticsDao.update(statistics_i);
            }
        }

        boolean mission_1_completed = false, mission_2_completed = false, mission_3_completed = false;

        if (ifMission1 || ifMission2) {
            weekly_mission_1_progress.setProgress(weekly_missions_db.weekly_completed_daily_missions);
            if (weekly_missions_db.weekly_completed_daily_missions >= 20) {
                weekly_mission_1.setTextColor(getResources().getColor(R.color.green_success));
                if (((weekly_missions_db.completed_weekly_missions / 100) % 10) == 1) {
                    mission_1_completed = true;
                    weekly_missions_db.completed_weekly_missions = weekly_missions_db.completed_weekly_missions + 100;
                    weeklyMissionsDao.update(weekly_missions_db);
                    pet_info_db.events_points = pet_info_db.events_points + 5;
                    petInfoDao.update(pet_info_db);
                    Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_6_label));
                    statistics_i.value++;
                    statisticsDao.update(statistics_i);
                }
            } else {
                weekly_mission_1_progress_text.setText(weekly_missions_db.weekly_completed_daily_missions + "/20");
            }

            weekly_mission_2_progress.setProgress(weekly_missions_db.weekly_completed_daily_missions);
            if (weekly_missions_db.weekly_completed_daily_missions == 28) {
                weekly_mission_2.setTextColor(getResources().getColor(R.color.green_success));
                if (((weekly_missions_db.completed_weekly_missions / 10) % 10) == 1) {
                    mission_2_completed = true;
                    weekly_missions_db.completed_weekly_missions = weekly_missions_db.completed_weekly_missions + 10;
                    weeklyMissionsDao.update(weekly_missions_db);
                    pet_info_db.events_points = pet_info_db.events_points + 20;
                    petInfoDao.update(pet_info_db);
                    Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_6_label));
                    statistics_i.value++;
                    statisticsDao.update(statistics_i);
                }
            } else {
                weekly_mission_2_progress_text.setText(weekly_missions_db.weekly_completed_daily_missions + "/28");
            }
        }

        if (ifMission3) {
            weekly_mission_3_progress.setProgress(weekly_missions_db.weekly_heart_points);
            if (weekly_missions_db.weekly_heart_points >= 150) {
                weekly_mission_3.setTextColor(getResources().getColor(R.color.green_success));
                if ((weekly_missions_db.completed_weekly_missions % 10) == 1) {
                    mission_3_completed = true;
                    weekly_missions_db.completed_weekly_missions = weekly_missions_db.completed_weekly_missions + 1;
                    weeklyMissionsDao.update(weekly_missions_db);
                    pet_info_db.events_points = pet_info_db.events_points + 10;
                    petInfoDao.update(pet_info_db);
                    Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_6_label));
                    statistics_i.value++;
                    statisticsDao.update(statistics_i);
                    statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_10_label));
                    statistics_i.value++;
                    statisticsDao.update(statistics_i);
                    Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_10_label));
                    if (statistics_i.value >= 52 && achievements_i.completed_type_id != 1) {
                        achievements_i.completed_type_id = 1;
                        achievementsDao.update(achievements_i);
                    }
                }
                if (weeklyMissionsGetAll.size() > 1) {
                    WeeklyMissions previous_weekly_missions_db = weeklyMissionsGetAll.get(weeklyMissionsGetAll.size() - 2);
                    if (previous_weekly_missions_db.previous_heart_points < 250) {
                        Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_10_label));
                        statistics_i.value = 1;
                        statisticsDao.update(statistics_i);
                    }
                }
            } else {
                weekly_mission_3_progress_text.setText(weekly_missions_db.weekly_completed_daily_missions + "/150");
            }
        }

        if (mission_1_completed || mission_2_completed || mission_3_completed) {
            if (weekly_missions_db.completed_weekly_missions == 222) {
                Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_8_label));
                statistics_i.value++;
                statisticsDao.update(statistics_i);
                Achievements achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_11_label));
                if (statistics_i.value >= 1 && achievements_i.completed_type_id != 1) {
                    achievements_i.completed_type_id = 1;
                    achievementsDao.update(achievements_i);
                }
                achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_12_label));
                if (statistics_i.value >= 10 && achievements_i.completed_type_id != 1) {
                    achievements_i.completed_type_id = 1;
                    achievementsDao.update(achievements_i);
                }
                achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_13_label));
                if (statistics_i.value >= 25 && achievements_i.completed_type_id != 1) {
                    achievements_i.completed_type_id = 1;
                    achievementsDao.update(achievements_i);
                }
                achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_14_label));
                if (statistics_i.value >= 50 && achievements_i.completed_type_id != 1) {
                    achievements_i.completed_type_id = 1;
                    achievementsDao.update(achievements_i);
                }
                statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_11_label));
                statistics_i.value++;
                statisticsDao.update(statistics_i);
                achievements_i = achievementsDao.getByLabel(getResources().getString(R.string.achievement_15_label));
                if (statistics_i.value >= 52 && achievements_i.completed_type_id != 1) {
                    achievements_i.completed_type_id = 1;
                    achievementsDao.update(achievements_i);
                }
            }
        }

        if (weekly_missions_db.completed_weekly_missions == 222 && weeklyMissionsGetAll.size() > 1) {
            WeeklyMissions previous_weekly_missions_db = weeklyMissionsGetAll.get(weeklyMissionsGetAll.size() - 2);
            if (previous_weekly_missions_db.completed_weekly_missions != 222) {
                Statistics statistics_i = statisticsDao.getByLabel(getResources().getString(R.string.statistics_11_label));
                statistics_i.value = 1;
                statisticsDao.update(statistics_i);
            }
        }
    }
}
