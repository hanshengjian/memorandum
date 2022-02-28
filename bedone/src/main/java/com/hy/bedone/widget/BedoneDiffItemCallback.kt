package com.hy.bedone.widget;

import androidx.recyclerview.widget.DiffUtil;

import com.hy.common.model.Bedone;

import java.util.List;

/**
 * @auther:hanshengjian
 * @date:2021/12/30
 */
public class RecyclerDiffItemCallback extends DiffUtil.Callback {

    private List<Bedone> olds;
    private List<Bedone> news;

    public RecyclerDiffItemCallback(List<Bedone> olds, List<Bedone> news) {
        this.olds = olds;
        this.news = news;
    }

    public List<Bedone> getOlds() {
        return olds;
    }

    public void setOlds(List<Bedone> olds) {
        this.olds = olds;
    }

    public List<Bedone> getNews() {
        return news;
    }

    public void setNews(List<Bedone> news) {
        this.news = news;
    }

    @Override
    public int getOldListSize() {
        return olds.size();
    }

    @Override
    public int getNewListSize() {
        return news.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olds.get(oldItemPosition).getId() == news.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Bedone oldNote = olds.get(oldItemPosition);
        Bedone newNote = news.get(newItemPosition);

        if (oldNote.getId() == newNote.getId() &&
                oldNote.getContent().equals(newNote.getContent()) &&
                oldNote.getType() == newNote.getType()) {
            return true;
        }
        return false;
    }
}
