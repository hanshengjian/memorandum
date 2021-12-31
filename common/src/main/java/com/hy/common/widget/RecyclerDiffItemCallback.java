package com.hy.common.widget;

import androidx.recyclerview.widget.DiffUtil;

import com.hy.common.model.Note;

import java.util.List;

/**
 * @auther:hanshengjian
 * @date:2021/12/30
 */
public class RecyclerDiffItemCallback extends DiffUtil.Callback {

    private List<Note> olds;
    private List<Note> news;

    public RecyclerDiffItemCallback(List<Note> olds, List<Note> news) {
        this.olds = olds;
        this.news = news;
    }

    public List<Note> getOlds() {
        return olds;
    }

    public void setOlds(List<Note> olds) {
        this.olds = olds;
    }

    public List<Note> getNews() {
        return news;
    }

    public void setNews(List<Note> news) {
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
        Note oldNote = olds.get(oldItemPosition);
        Note newNote = olds.get(newItemPosition);

        if (oldNote.getId() == newNote.getId() &&
                oldNote.getContent().equals(newNote.getContent()) &&
                oldNote.getType() == newNote.getType() &&
                oldNote.getTitle().equals(newNote.getTitle())) {
            return true;
        }
        return false;
    }
}
