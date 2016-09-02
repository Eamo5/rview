/*
 * Copyright (C) 2016 Jorge Ruesga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ruesga.rview.misc;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.TextView;

import com.ruesga.rview.R;
import com.ruesga.rview.annotations.ProguardIgnored;
import com.ruesga.rview.fragments.ChangeDetailsFragment;
import com.ruesga.rview.gerrit.model.CommitInfo;
import com.ruesga.rview.gerrit.model.FileInfo;
import com.ruesga.rview.gerrit.model.FileStatus;
import com.ruesga.rview.gerrit.model.GitPersonalInfo;
import com.ruesga.rview.gerrit.model.SubmitType;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ProguardIgnored
@SuppressWarnings("unused")
public class Formatter {
    private static final Map<Locale, PrettyTime> sPrettyTimeMap = new HashMap<>();

    @BindingAdapter("prettyDateTime")
    public static void toPrettyDateTime(TextView view, Date date) {
        if (date == null) {
            view.setText(null);
            return;
        }

        Locale locale = AndroidHelper.getCurrentLocale(view.getContext());
        if (!sPrettyTimeMap.containsKey(locale)) {
            sPrettyTimeMap.put(locale, new PrettyTime(locale));
        }
        view.setText(sPrettyTimeMap.get(locale).format(date));
    }

    @BindingAdapter("compressedText")
    public static void toCompressedText(TextView view, String value) {
        if (value == null) {
            view.setText(null);
            return;
        }
        view.setText(value.replaceAll("\n", " ").trim());
    }

    @BindingAdapter("commitMessage")
    @SuppressWarnings("deprecation")
    public static void toCommitMessage(TextView view, CommitInfo info) {
        if (info == null || info.message == null) {
            view.setText(null);
            return;
        }

        String message = info.message.substring(info.subject.length()).trim();
        view.setText(message);
    }

    @BindingAdapter("committer")
    public static void toCommitter(TextView view, GitPersonalInfo info) {
        if (info == null) {
            view.setText(null);
            return;
        }

        Locale locale = AndroidHelper.getCurrentLocale(view.getContext());
        if (!sPrettyTimeMap.containsKey(locale)) {
            sPrettyTimeMap.put(locale, new PrettyTime(locale));
        }
        String date = sPrettyTimeMap.get(locale).format(info.date);
        String txt = view.getContext().getString(
                R.string.committer_format, info.name, info.email, date);
        view.setText(txt);
    }

    @BindingAdapter("score")
    public static void toScore(TextView view, Integer score) {
        if (score == null) {
            view.setText(null);
            return;
        }

        String txt = (score > 0 ? "+" : "") + String.valueOf(score);
        view.setText(txt);
    }

    @BindingAdapter("submitType")
    public static void toSubmitType(TextView view, SubmitType submitType) {
        if (submitType == null) {
            view.setText(null);
            return;
        }

        view.setText(submitType.toString().replace("_", " "));
    }

    @BindingAdapter("fileStatus")
    public static void toFileStatus(TextView view, ChangeDetailsFragment.FileItemModel item) {
        if (item == null) {
            view.setText(null);
            return;
        }

        String status = "";
        if (item.info.status.equals(FileStatus.R)) {
            status = "[" + view.getContext().getString(R.string.file_status_renamed) + "] ";
        } else if (item.info.status.equals(FileStatus.C)) {
            status = "[" + view.getContext().getString(R.string.file_status_copied) + "] ";
        } else if (item.info.status.equals(FileStatus.W)) {
            status = "[" + view.getContext().getString(R.string.file_status_rewritten) + "] ";
        }
        String txt = status;
        if (!TextUtils.isEmpty(item.info.oldPath)) {
            txt += item.info.oldPath + " -> ";
        }
        txt += item.file;
        view.setText(txt);
    }

    @BindingAdapter("addedVsDeleted")
    public static void toAddedVsRemoved(TextView view, FileInfo info) {
        if (info == null) {
            view.setText(null);
            return;
        }

        final Resources res = view.getResources();
        String added = null;
        if (info.linesInserted != null && info.linesInserted > 0) {
            added = res.getQuantityString(
                    R.plurals.files_added, info.linesInserted, info.linesInserted);
        }
        String deleted = null;
        if (info.linesDeleted != null && info.linesDeleted > 0) {
            deleted = res.getQuantityString(
                    R.plurals.files_deleted, info.linesDeleted, info.linesDeleted);
        }

        String txt = null;
        if (added != null && deleted != null) {
            txt = res.getString(R.string.added_vs_deleted, added, deleted);
        } else if (added != null) {
            txt = added;
        } else if (deleted != null) {
            txt = deleted;
        }
        view.setText(txt);
    }
}