package com.greensmod.FitFoster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementElementAdapter extends RecyclerView.Adapter<AchievementElementAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<AchievementElement> achievements;

    AchievementElementAdapter(Context context, List<AchievementElement> achievements) {
        this.achievements = achievements;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AchievementElementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AchievementElementAdapter.ViewHolder holder, int position) {
        AchievementElement achievementElement = achievements.get(position);
        holder.nameView.setText(achievementElement.getName());
        holder.descriptionView.setText(achievementElement.getDescription());

        if (achievementElement.getCompleted_type_id() == 1) {
            holder.constraint_layoutView.setBackground(inflater.getContext().getDrawable(R.drawable.border_achievement_completed));
            holder.progressBarView.setVisibility(View.GONE);
            holder.progress_textView.setVisibility(View.GONE);
        } else if (achievementElement.getCompleted_type_id() == 0) {
            holder.constraint_layoutView.setBackground(inflater.getContext().getDrawable(R.drawable.border_achievement_uncompleted));
            holder.progressBarView.setVisibility(View.GONE);
            holder.progress_textView.setVisibility(View.GONE);
            if (achievementElement.getType_id() == 2) {
                System.out.println(achievementElement.getName());
                StatisticsRoomDatabase dbStatistics = App.getInstance().getDatabaseStatistics();
                StatisticsDao statisticsDao = dbStatistics.statisticsDao();

                String[][] labels_achievements_list = {
                        {inflater.getContext().getResources().getString(R.string.achievement_6_label), "1000000", Integer.toString(statisticsDao.getByLabel(inflater.getContext().getResources().getString(R.string.statistics_1_label)).value)},
                        {inflater.getContext().getResources().getString(R.string.achievement_10_label), "52", Integer.toString(statisticsDao.getByLabel(inflater.getContext().getResources().getString(R.string.statistics_10_label)).value)},
                        {inflater.getContext().getResources().getString(R.string.achievement_15_label), "52", Integer.toString(statisticsDao.getByLabel(inflater.getContext().getResources().getString(R.string.statistics_11_label)).value)}
                };

                for (int i = 0; i < labels_achievements_list.length; i++) {
                    if (labels_achievements_list[i][0].equals(achievementElement.getLabel())) {
                        holder.progressBarView.setVisibility(View.VISIBLE);
                        holder.progress_textView.setVisibility(View.VISIBLE);
                        holder.progressBarView.setMax(Integer.parseInt(labels_achievements_list[i][1]));
                        holder.progressBarView.setProgress(Integer.parseInt(labels_achievements_list[i][2]));
                        holder.progress_textView.setText(Integer.parseInt(labels_achievements_list[i][2]) + "/" + Integer.parseInt(labels_achievements_list[i][1]));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout constraint_layoutView;
        final TextView nameView, descriptionView, progress_textView;
        final ProgressBar progressBarView;

        ViewHolder(View view) {
            super(view);
            constraint_layoutView = (ConstraintLayout) view.findViewById(R.id.constraint_layout);
            nameView = (TextView) view.findViewById(R.id.name);
            descriptionView = (TextView) view.findViewById(R.id.description);
            progressBarView = (ProgressBar) view.findViewById(R.id.progress);
            progress_textView = (TextView) view.findViewById(R.id.progress_text);
        }
    }
}