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
package com.ruesga.rview.gerrit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.ruesga.rview.gerrit.adapters.GerritApprovalInfoAdapter;
import com.ruesga.rview.gerrit.adapters.GerritBas64Adapter;
import com.ruesga.rview.gerrit.adapters.GerritServerVersionAdapter;
import com.ruesga.rview.gerrit.adapters.GerritUtcDateAdapter;
import com.ruesga.rview.gerrit.filter.AccountQuery;
import com.ruesga.rview.gerrit.filter.ChangeQuery;
import com.ruesga.rview.gerrit.filter.Option;
import com.ruesga.rview.gerrit.model.AbandonInput;
import com.ruesga.rview.gerrit.model.AccountDetailInfo;
import com.ruesga.rview.gerrit.model.AccountInfo;
import com.ruesga.rview.gerrit.model.AccountInput;
import com.ruesga.rview.gerrit.model.AccountNameInput;
import com.ruesga.rview.gerrit.model.AccountOptions;
import com.ruesga.rview.gerrit.model.ActionInfo;
import com.ruesga.rview.gerrit.model.AddGpgKeyInput;
import com.ruesga.rview.gerrit.model.AddReviewerResultInfo;
import com.ruesga.rview.gerrit.model.ApprovalInfo;
import com.ruesga.rview.gerrit.model.Base64Data;
import com.ruesga.rview.gerrit.model.BlameInfo;
import com.ruesga.rview.gerrit.model.Capability;
import com.ruesga.rview.gerrit.model.CapabilityInfo;
import com.ruesga.rview.gerrit.model.ChangeEditMessageInput;
import com.ruesga.rview.gerrit.model.ChangeInfo;
import com.ruesga.rview.gerrit.model.ChangeInput;
import com.ruesga.rview.gerrit.model.ChangeOptions;
import com.ruesga.rview.gerrit.model.CherryPickInput;
import com.ruesga.rview.gerrit.model.CommentInfo;
import com.ruesga.rview.gerrit.model.CommentInput;
import com.ruesga.rview.gerrit.model.CommitInfo;
import com.ruesga.rview.gerrit.model.ConfigInfo;
import com.ruesga.rview.gerrit.model.ConfigInput;
import com.ruesga.rview.gerrit.model.ContributorAgreementInfo;
import com.ruesga.rview.gerrit.model.ContributorAgreementInput;
import com.ruesga.rview.gerrit.model.DeleteGpgKeyInput;
import com.ruesga.rview.gerrit.model.DeleteProjectWatchInput;
import com.ruesga.rview.gerrit.model.DeleteVoteInput;
import com.ruesga.rview.gerrit.model.DiffInfo;
import com.ruesga.rview.gerrit.model.DiffPreferencesInfo;
import com.ruesga.rview.gerrit.model.DiffPreferencesInput;
import com.ruesga.rview.gerrit.model.EditFileInfo;
import com.ruesga.rview.gerrit.model.EditInfo;
import com.ruesga.rview.gerrit.model.EditPreferencesInfo;
import com.ruesga.rview.gerrit.model.EditPreferencesInput;
import com.ruesga.rview.gerrit.model.EmailInfo;
import com.ruesga.rview.gerrit.model.EmailInput;
import com.ruesga.rview.gerrit.model.FileInfo;
import com.ruesga.rview.gerrit.model.FixInput;
import com.ruesga.rview.gerrit.model.GcInput;
import com.ruesga.rview.gerrit.model.GpgKeyInfo;
import com.ruesga.rview.gerrit.model.GroupInfo;
import com.ruesga.rview.gerrit.model.HeadInput;
import com.ruesga.rview.gerrit.model.HttpPasswordInput;
import com.ruesga.rview.gerrit.model.IncludeInInfo;
import com.ruesga.rview.gerrit.model.MergeableInfo;
import com.ruesga.rview.gerrit.model.MoveInput;
import com.ruesga.rview.gerrit.model.NewChangeEditInput;
import com.ruesga.rview.gerrit.model.OAuthTokenInfo;
import com.ruesga.rview.gerrit.model.PreferencesInfo;
import com.ruesga.rview.gerrit.model.PreferencesInput;
import com.ruesga.rview.gerrit.model.ProjectAccessInfo;
import com.ruesga.rview.gerrit.model.ProjectDescriptionInput;
import com.ruesga.rview.gerrit.model.ProjectInfo;
import com.ruesga.rview.gerrit.model.ProjectInput;
import com.ruesga.rview.gerrit.model.ProjectParentInput;
import com.ruesga.rview.gerrit.model.ProjectType;
import com.ruesga.rview.gerrit.model.ProjectWatchInfo;
import com.ruesga.rview.gerrit.model.ProjectWatchInput;
import com.ruesga.rview.gerrit.model.RebaseInput;
import com.ruesga.rview.gerrit.model.RelatedChangesInfo;
import com.ruesga.rview.gerrit.model.RenameChangeEditInput;
import com.ruesga.rview.gerrit.model.RepositoryStatisticsInfo;
import com.ruesga.rview.gerrit.model.RestoreChangeEditInput;
import com.ruesga.rview.gerrit.model.RestoreInput;
import com.ruesga.rview.gerrit.model.RevertInput;
import com.ruesga.rview.gerrit.model.ReviewInfo;
import com.ruesga.rview.gerrit.model.ReviewInput;
import com.ruesga.rview.gerrit.model.ReviewerInfo;
import com.ruesga.rview.gerrit.model.ReviewerInput;
import com.ruesga.rview.gerrit.model.RuleInput;
import com.ruesga.rview.gerrit.model.ServerInfo;
import com.ruesga.rview.gerrit.model.ServerVersion;
import com.ruesga.rview.gerrit.model.SshKeyInfo;
import com.ruesga.rview.gerrit.model.StarInput;
import com.ruesga.rview.gerrit.model.SubmitInfo;
import com.ruesga.rview.gerrit.model.SubmitInput;
import com.ruesga.rview.gerrit.model.SubmitRecordInfo;
import com.ruesga.rview.gerrit.model.SubmitType;
import com.ruesga.rview.gerrit.model.SubmittedTogetherInfo;
import com.ruesga.rview.gerrit.model.SubmittedTogetherOptions;
import com.ruesga.rview.gerrit.model.SuffixMode;
import com.ruesga.rview.gerrit.model.SuggestedReviewerInfo;
import com.ruesga.rview.gerrit.model.TopicInput;
import com.ruesga.rview.gerrit.model.UsernameInput;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GerritApiClient implements GerritApi {

    private final static Map<String, GerritApiClient> sInstances = new HashMap<>();

    private final GerritApi mService;
    private final PlatformAbstractionLayer mAbstractionLayer;

    private GerritApiClient(String endpoint, PlatformAbstractionLayer abstractionLayer) {
        mAbstractionLayer = abstractionLayer;

        // OkHttp client
        OkHttpClient client = OkHttpHelper.getUnsafeClientBuilder()
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(createLoggingInterceptor())
                .addInterceptor(createHeadersInterceptor())
                .build();

        // Gson adapter
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setVersion(VERSION)
                .generateNonExecutableJson()
                .setLenient();
        registerCustomAdapters(gsonBuilder);
        GsonConverterFactory gsonFactory = GsonConverterFactory.create(gsonBuilder.create());

        // RxJava adapter
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(rxAdapter)
                .build();

        // Build the api
        mService = retrofit.create(GerritApi.class);
    }

    public static GerritApiClient getInstance(String endpoint,
            PlatformAbstractionLayer abstractionLayer) {
        // Sanitize endpoint
        if (!endpoint.endsWith("/")) {
            endpoint += "/";
        }

        if (!sInstances.containsKey(endpoint)) {
            sInstances.put(endpoint, new GerritApiClient(endpoint, abstractionLayer));
        }
        return sInstances.get(endpoint);
    }

    private HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new Logger() {
            @Override
            public void log(String message) {
                mAbstractionLayer.log(message);
            }
        });
        logging.setLevel(mAbstractionLayer.isDebugBuild()
                ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    private Interceptor createHeadersInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                if (!mAbstractionLayer.isDebugBuild()) {
                    requestBuilder.header("Accept", "application/json");
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    private void registerCustomAdapters(GsonBuilder builder) {
        builder.registerTypeAdapter(Date.class, new GerritUtcDateAdapter());
        builder.registerTypeAdapter(ServerVersion.class, new GerritServerVersionAdapter());
        builder.registerTypeAdapter(ApprovalInfo.class, new GerritApprovalInfoAdapter());
        builder.registerTypeAdapter(Base64Data.class, new GerritBas64Adapter(mAbstractionLayer));
    }


    // ===============================
    // Gerrit access endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-access.html"
    // ===============================

    @Override
    public Observable<Map<String, ProjectAccessInfo>> getAccessRights(@NonNull String[] names) {
        return mService.getAccessRights(names);
    }


    // ===============================
    // Gerrit accounts endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-accounts.html"
    // ===============================

    @Override
    public Observable<List<AccountInfo>> getAccountsSuggestions(
            @NonNull String query, @Nullable Integer count) {
        return mService.getAccountsSuggestions(query, count);
    }

    @Override
    public Observable<List<AccountInfo>> getAccounts(
            @NonNull AccountQuery query, @Nullable Integer count,
            @Nullable Integer start, @Nullable AccountOptions[] options) {
        return mService.getAccounts(query, count, start, options);
    }

    @Override
    public Observable<AccountInfo> getAccount(@NonNull String accountId) {
        return mService.getAccount(accountId);
    }

    @Override
    public Observable<AccountInfo> createAccount(
            @NonNull String username, @NonNull AccountInput input) {
        return mService.createAccount(username, input);
    }

    @Override
    public Observable<AccountDetailInfo> getAccountDetails(@NonNull String accountId) {
        return mService.getAccountDetails(accountId);
    }

    @Override
    public Observable<String> getAccountName(@NonNull String accountId) {
        return mService.getAccountName(accountId);
    }

    @Override
    public Observable<String> setAccountName(
            @NonNull String accountId, @NonNull AccountNameInput input) {
        return mService.setAccountName(accountId, input);
    }

    @Override
    public Observable<Void> deleteAccountName(@NonNull String accountId) {
        return mService.deleteAccountName(accountId);
    }

    @Override
    public Observable<String> getAccountUsername(@NonNull String accountId) {
        return mService.getAccountUsername(accountId);
    }

    @Override
    public Observable<String> setAccountUsername(
            @NonNull String accountId, @NonNull UsernameInput input) {
        return mService.setAccountUsername(accountId, input);
    }

    @Override
    public Observable<String> isAccountActive(@NonNull String accountId) {
        return mService.isAccountActive(accountId);
    }

    @Override
    public Observable<Void> setAccountAsActive(@NonNull String accountId) {
        return mService.setAccountAsActive(accountId);
    }

    @Override
    public Observable<Void> setAccountAsInactive(@NonNull String accountId) {
        return mService.setAccountAsInactive(accountId);
    }

    @Override
    public Observable<String> getHttpPassword(@NonNull String accountId) {
        return mService.getHttpPassword(accountId);
    }

    @Override
    public Observable<String> setHttpPassword(
            @NonNull String accountId, @NonNull HttpPasswordInput input) {
        return mService.setHttpPassword(accountId, input);
    }

    @Override
    public Observable<Void> deleteHttpPassword(@NonNull String accountId) {
        return mService.deleteHttpPassword(accountId);
    }

    @Override
    public Observable<OAuthTokenInfo> getOAuthToken(@NonNull String accountId) {
        return mService.getOAuthToken(accountId);
    }

    @Override
    public Observable<List<EmailInfo>> getAccountEmails(@NonNull String accountId) {
        return mService.getAccountEmails(accountId);
    }

    @Override
    public Observable<EmailInfo> getAccountEmail(
            @NonNull String accountId, @NonNull String emailId) {
        return mService.getAccountEmail(accountId, emailId);
    }

    @Override
    public Observable<EmailInfo> createAccountEmail(@NonNull String accountId,
            @NonNull String emailId, @NonNull EmailInput input) {
        return mService.createAccountEmail(accountId, emailId, input);
    }

    @Override
    public Observable<Void> deleteAccountEmail(@NonNull String accountId, @NonNull String emailId) {
        return mService.deleteAccountEmail(accountId, emailId);
    }

    @Override
    public Observable<Void> setAccountPreferredEmail(
            @NonNull String accountId, @NonNull String emailId) {
        return mService.setAccountPreferredEmail(accountId, emailId);
    }

    @Override
    public Observable<List<SshKeyInfo>> getAccountSshKeys(@NonNull String accountId) {
        return mService.getAccountSshKeys(accountId);
    }

    @Override
    public Observable<SshKeyInfo> getAccountSshKey(@NonNull String accountId, int sshKeyId) {
        return mService.getAccountSshKey(accountId, sshKeyId);
    }

    @Override
    public Observable<SshKeyInfo> addAccountSshKey(
            @NonNull String accountId, @NonNull String encodedKey) {
        return mService.addAccountSshKey(accountId, encodedKey);
    }

    @Override
    public Observable<Void> deleteAccountSshKey(@NonNull String accountId, int sshKeyId) {
        return mService.deleteAccountSshKey(accountId, sshKeyId);
    }

    @Override
    public Observable<List<GpgKeyInfo>> getAccountGpgKeys(@NonNull String accountId) {
        return mService.getAccountGpgKeys(accountId);
    }

    @Override
    public Observable<GpgKeyInfo> getAccountGpgKey(
            @NonNull String accountId, @NonNull String gpgKeyId) {
        return mService.getAccountGpgKey(accountId, gpgKeyId);
    }

    @Override
    public Observable<Map<String, GpgKeyInfo>> addAccountGpgKeys(
            @NonNull String accountId, @NonNull AddGpgKeyInput input) {
        return mService.addAccountGpgKeys(accountId, input);
    }

    @Override
    public Observable<Map<String, GpgKeyInfo>> deleteAccountGpgKeys(
            @NonNull String accountId, @NonNull DeleteGpgKeyInput input) {
        return mService.deleteAccountGpgKeys(accountId, input);
    }

    @Override
    public Observable<CapabilityInfo> getAccountCapabilities(
            @NonNull String accountId, @Nullable Capability[] filter) {
        return mService.getAccountCapabilities(accountId, filter);
    }

    @Override
    public Observable<String> hasAccountCapability(
            @NonNull String accountId, @NonNull Capability capabilityId) {
        return mService.hasAccountCapability(accountId, capabilityId);
    }

    @Override
    public Observable<List<GroupInfo>> getAccountGroups(@NonNull String accountId) {
        return mService.getAccountGroups(accountId);
    }

    @Override
    public Observable<Response> getAccountAvatar(@NonNull String accountId) {
        return mService.getAccountAvatar(accountId);
    }

    @Override
    public Observable<String> getAccountAvatarChangeUrl(@NonNull String accountId) {
        return mService.getAccountAvatarChangeUrl(accountId);
    }

    @Override
    public Observable<PreferencesInfo> getAccountPreferences(@NonNull String accountId) {
        return mService.getAccountPreferences(accountId);
    }

    @Override
    public Observable<PreferencesInfo> setAccountPreferences(
            @NonNull String accountId,
            @NonNull PreferencesInput input) {
        return mService.setAccountPreferences(accountId, input);
    }

    @Override
    public Observable<DiffPreferencesInfo> getAccountDiffPreferences(@NonNull String accountId) {
        return mService.getAccountDiffPreferences(accountId);
    }

    @Override
    public Observable<DiffPreferencesInfo> setAccountDiffPreferences(
            @NonNull String accountId,
            @NonNull DiffPreferencesInput input) {
        return mService.setAccountDiffPreferences(accountId, input);
    }

    @Override
    public Observable<EditPreferencesInfo> getAccountEditPreferences(@NonNull String accountId) {
        return mService.getAccountEditPreferences(accountId);
    }

    @Override
    public Observable<EditPreferencesInfo> setAccountEditPreferences(
            @NonNull String accountId,
            @NonNull EditPreferencesInput input) {
        return mService.setAccountEditPreferences(accountId, input);
    }

    @Override
    public Observable<List<ProjectWatchInfo>> getAccountWatchedProjects(@NonNull String accountId) {
        return mService.getAccountWatchedProjects(accountId);
    }

    @Override
    public Observable<List<ProjectWatchInfo>> addOrUpdateAccountWatchedProjects(
            @NonNull String accountId, @NonNull ProjectWatchInput[] input) {
        return mService.addOrUpdateAccountWatchedProjects(accountId, input);
    }

    @Override
    public Observable<Void> deleteAccountWatchedProjects(
            @NonNull String accountId, @NonNull DeleteProjectWatchInput[] input) {
        return mService.deleteAccountWatchedProjects(accountId, input);
    }

    @Override
    public Observable<List<ChangeInfo>> getDefaultStarredChanges(@NonNull String accountId) {
        return mService.getDefaultStarredChanges(accountId);
    }

    @Override
    public Observable<Void> putDefaultStarOnChange(
            @NonNull String accountId, @NonNull String changeId) {
        return mService.putDefaultStarOnChange(accountId, changeId);
    }

    @Override
    public Observable<Void> removeDefaultStarFromChange(
            @NonNull String accountId, @NonNull String changeId) {
        return mService.removeDefaultStarFromChange(accountId, changeId);
    }

    @Override
    public Observable<List<ChangeInfo>> getStarredChanges(@NonNull String accountId) {
        return mService.getStarredChanges(accountId);
    }

    @Override
    public Observable<List<String>> getStarLabelsFromChange(
            @NonNull String accountId, @NonNull String changeId) {
        return mService.getStarLabelsFromChange(accountId, changeId);
    }

    @Override
    public Observable<List<String>> updateStarLabelsFromChange(@NonNull String accountId,
            @NonNull String changeId, @NonNull StarInput input) {
        return mService.updateStarLabelsFromChange(accountId, changeId, input);
    }

    @Override
    public Observable<List<ContributorAgreementInfo>> getContributorAgreements(
            @NonNull String accountId) {
        return mService.getContributorAgreements(accountId);
    }

    @Override
    public Observable<String> signContributorAgreement(
            @NonNull String accountId, @NonNull ContributorAgreementInput input) {
        return mService.signContributorAgreement(accountId, input);
    }



    // ===============================
    // Gerrit changes endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html"
    // ===============================

    @Override
    public Observable<ChangeInfo> createChange(@NonNull ChangeInput input) {
        return mService.createChange(input);
    }

    @Override
    public Observable<List<ChangeInfo>> getChanges(
            @NonNull ChangeQuery query, @Nullable Integer count,
            @Nullable Integer start, @Nullable ChangeOptions[] options) {
        return mService.getChanges(query, count, start, options);
    }

    @Override
    public Observable<ChangeInfo> getChange(
            @NonNull String changeId, @Nullable ChangeOptions[] options) {
        return mService.getChange(changeId, options);
    }

    @Override
    public Observable<ChangeInfo> getChangeDetail(
            @NonNull String changeId, @Nullable ChangeOptions[] options) {
        return mService.getChangeDetail(changeId, options);
    }

    @Override
    public Observable<String> getChangeTopic(@NonNull String changeId) {
        return mService.getChangeTopic(changeId);
    }

    @Override
    public Observable<String> setChangeTopic(@NonNull String changeId, @NonNull TopicInput input) {
        return mService.setChangeTopic(changeId, input);
    }

    @Override
    public Observable<Void> deleteChangeTopic(@NonNull String changeId) {
        return mService.deleteChangeTopic(changeId);
    }

    @Override
    public Observable<ChangeInfo> abandonChange(
            @NonNull String changeId, @NonNull AbandonInput input) {
        return mService.abandonChange(changeId, input);
    }

    @Override
    public Observable<ChangeInfo> restoreChange(
            @NonNull String changeId, @NonNull RestoreInput input) {
        return mService.restoreChange(changeId, input);
    }

    @Override
    public Observable<ChangeInfo> rebaseChange(
            @NonNull String changeId, @NonNull RebaseInput input) {
        return mService.rebaseChange(changeId, input);
    }

    @Override
    public Observable<ChangeInfo> moveChange(
            @NonNull String changeId, @NonNull MoveInput input) {
        return mService.moveChange(changeId, input);
    }

    @Override
    public Observable<ChangeInfo> revertChange(
            @NonNull String changeId, @NonNull RevertInput input) {
        return mService.revertChange(changeId, input);
    }

    @Override
    public Observable<ChangeInfo> submitChange(
            @NonNull String changeId, @NonNull SubmitInput input) {
        return mService.submitChange(changeId, input);
    }

    @Override
    public Observable<SubmittedTogetherInfo> getChangesSubmittedTogether(
            @NonNull String changeId, @Nullable SubmittedTogetherOptions[] options) {
        return mService.getChangesSubmittedTogether(changeId, options);
    }

    @Override
    public Observable<Void> publishDraftChange(@NonNull String changeId) {
        return mService.publishDraftChange(changeId);
    }

    @Override
    public Observable<Void> deleteDraftChange(@NonNull String changeId) {
        return mService.deleteDraftChange(changeId);
    }

    @Override
    public Observable<IncludeInInfo> getChangeIncludedIn(@NonNull String changeId) {
        return mService.getChangeIncludedIn(changeId);
    }

    @Override
    public Observable<Void> indexChange(@NonNull String changeId) {
        return mService.indexChange(changeId);
    }

    @Override
    public Observable<Map<String, List<CommentInfo>>> getChangeComments(@NonNull String changeId) {
        return mService.getChangeComments(changeId);
    }

    @Override
    public Observable<Map<String, List<CommentInfo>>> getChangeDraftComments(
            @NonNull String changeId) {
        return mService.getChangeDraftComments(changeId);
    }

    @Override
    public Observable<ChangeInfo> checkChange(@NonNull String changeId) {
        return mService.checkChange(changeId);
    }

    @Override
    public Observable<ChangeInfo> fixChange(@NonNull String changeId, @NonNull FixInput input) {
        return mService.fixChange(changeId, input);
    }

    @Override
    public Observable<EditInfo> getChangeEdit(@NonNull String changeId,
            @Nullable Option list, @Nullable String base, @Nullable Option downloadCommands) {
        return mService.getChangeEdit(changeId, list, base, downloadCommands);
    }

    @Override
    public Observable<Void> setChangeEdit(
            @NonNull String changeId, @NonNull String fileId, @NonNull RequestBody data) {
        return mService.setChangeEdit(changeId, fileId, data);
    }

    @Override
    public Observable<Void> restoreChangeEdit(
            @NonNull String changeId, @NonNull RestoreChangeEditInput input) {
        return mService.restoreChangeEdit(changeId, input);
    }

    @Override
    public Observable<Void> renameChangeEdit(
            @NonNull String changeId, @NonNull RenameChangeEditInput input) {
        return mService.renameChangeEdit(changeId, input);
    }

    @Override
    public Observable<Void> newChangeEdit(
            @NonNull String changeId, @NonNull NewChangeEditInput input) {
        return mService.newChangeEdit(changeId, input);
    }

    @Override
    public Observable<Void> deleteChangeEditFile(@NonNull String changeId, @NonNull String fileId) {
        return mService.deleteChangeEditFile(changeId, fileId);
    }

    @Override
    public Observable<Base64Data> getChangeEditFileContent(
            @NonNull String changeId, @NonNull String fileId, @Nullable String base) {
        return mService.getChangeEditFileContent(changeId, fileId, base);
    }

    @Override
    public Observable<EditFileInfo> getChangeEditFileMetadata(
            @NonNull String changeId, @NonNull String fileId) {
        return mService.getChangeEditFileMetadata(changeId, fileId);
    }

    @Override
    public Observable<String> getChangeEditMessage(@NonNull String changeId) {
        return mService.getChangeEditMessage(changeId);
    }

    @Override
    public Observable<Void> setChangeEditMessage(
            @NonNull String changeId, @NonNull ChangeEditMessageInput input) {
        return mService.setChangeEditMessage(changeId, input);
    }

    @Override
    public Observable<Void> publishChangeEdit(@NonNull String changeId) {
        return mService.publishChangeEdit(changeId);
    }

    @Override
    public Observable<Void> rebaseChangeEdit(@NonNull String changeId) {
        return mService.rebaseChangeEdit(changeId);
    }

    @Override
    public Observable<Void> deleteChangeEdit(@NonNull String changeId) {
        return mService.deleteChangeEdit(changeId);
    }

    @Override
    public Observable<List<ReviewerInfo>> getChangeReviewers(@NonNull String changeId) {
        return mService.getChangeReviewers(changeId);
    }

    @Override
    public Observable<List<SuggestedReviewerInfo>> getChangeSuggestedReviewers(
            @NonNull String changeId, @NonNull String query, @Nullable Integer count) {
        return mService.getChangeSuggestedReviewers(changeId, query, count);
    }

    @Override
    public Observable<List<ReviewerInfo>> getChangeReviewer(
            @NonNull String changeId, @NonNull String accountId) {
        return mService.getChangeReviewer(changeId, accountId);
    }

    @Override
    public Observable<AddReviewerResultInfo> addChangeReviewer(
            @NonNull String changeId, @NonNull ReviewerInput input) {
        return mService.addChangeReviewer(changeId, input);
    }

    @Override
    public Observable<Void> deleteChangeReviewer(
            @NonNull String changeId, @NonNull String accountId) {
        return mService.deleteChangeReviewer(changeId, accountId);
    }

    @Override
    public Observable<Map<String, Integer>> getChangeReviewerVotes(
            @NonNull String changeId, @NonNull String accountId) {
        return mService.getChangeReviewerVotes(changeId, accountId);
    }

    @Override
    public Observable<Void> deleteChangeReviewerVote(@NonNull String changeId,
            @NonNull String accountId, @NonNull String labelId, @NonNull DeleteVoteInput input) {
        return mService.deleteChangeReviewerVote(changeId, accountId, labelId, input);
    }

    @Override
    public Observable<CommitInfo> getChangeRevisionCommit(
            @NonNull String changeId, @NonNull String revisionId, @Nullable Option links) {
        return mService.getChangeRevisionCommit(changeId, revisionId, links);
    }

    @Override
    public Observable<Map<String, ActionInfo>> getChangeRevisionActions(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionActions(changeId, revisionId);
    }

    @Override
    public Observable<ChangeInfo> getChangeRevisionReview(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionReview(changeId, revisionId);
    }

    @Override
    public Observable<ReviewInfo> setChangeRevisionRelatedChanges(@NonNull String changeId,
            @NonNull String revisionId, @NonNull ReviewInput input) {
        return mService.setChangeRevisionRelatedChanges(changeId, revisionId, input);
    }

    @Override
    public Observable<RelatedChangesInfo> getChangeRevisionRelatedChanges(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionRelatedChanges(changeId, revisionId);
    }

    @Override
    public Observable<ChangeInfo> rebaseChangeRevision(
            @NonNull String changeId, @NonNull String revisionId, @NonNull RebaseInput input) {
        return mService.rebaseChangeRevision(changeId, revisionId, input);
    }

    @Override
    public Observable<SubmitInfo> submitChangeRevision(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.submitChangeRevision(changeId, revisionId);
    }

    @Override
    public Observable<SubmitInfo> publishChangeDraftRevision(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.publishChangeDraftRevision(changeId, revisionId);
    }

    @Override
    public Observable<SubmitInfo> deleteChangeDraftRevision(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.deleteChangeDraftRevision(changeId, revisionId);
    }

    @Override
    public Observable<Base64Data> getChangeRevisionPatch(@NonNull String changeId,
            @NonNull String revisionId, @Nullable Option zip, @Nullable Option download) {
        return mService.getChangeRevisionPatch(changeId, revisionId, zip, download);
    }

    @Override
    public Observable<MergeableInfo> getChangeRevisionMergeable(@NonNull String changeId,
            @NonNull String revisionId, @Nullable Option otherBranches) {
        return mService.getChangeRevisionMergeable(changeId, revisionId, otherBranches);
    }

    @Override
    public Observable<SubmitType> getChangeRevisionSubmitType(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionSubmitType(changeId, revisionId);
    }

    @Override
    public Observable<SubmitType> testChangeRevisionSubmitType(@NonNull String changeId,
            @NonNull String revisionId, @NonNull RuleInput input) {
        return mService.testChangeRevisionSubmitType(changeId, revisionId, input);
    }

    @Override
    public Observable<List<SubmitRecordInfo>> testChangeRevisionSubmitRule(
            @NonNull String changeId, @NonNull String revisionId, @NonNull RuleInput input) {
        return mService.testChangeRevisionSubmitRule(changeId, revisionId, input);
    }

    @Override
    public Observable<Map<String, List<CommentInfo>>> getChangeRevisionDrafts(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionDrafts(changeId, revisionId);
    }

    @Override
    public Observable<CommentInfo> createChangeRevisionDraft(
            @NonNull String changeId, @NonNull String revisionId, @NonNull CommentInput input) {
        return mService.createChangeRevisionDraft(changeId, revisionId, input);
    }

    @Override
    public Observable<CommentInfo> getChangeRevisionDraft(
            @NonNull String changeId, @NonNull String revisionId, @NonNull String draftId) {
        return mService.getChangeRevisionDraft(changeId, revisionId, draftId);
    }

    @Override
    public Observable<CommentInfo> updateChangeRevisionDraft(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String draftId, @NonNull CommentInput input) {
        return mService.updateChangeRevisionDraft(changeId, revisionId, draftId, input);
    }

    @Override
    public Observable<Void> deleteChangeRevisionDraft(
            @NonNull String changeId, @NonNull String revisionId, @NonNull String draftId) {
        return mService.deleteChangeRevisionDraft(changeId, revisionId, draftId);
    }

    @Override
    public Observable<Map<String, List<CommentInfo>>> getChangeRevisionComments(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionComments(changeId, revisionId);
    }

    @Override
    public Observable<CommentInfo> getChangeRevisionComment(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String commentId) {
        return mService.getChangeRevisionComment(changeId, revisionId, commentId);
    }

    @Override
    public Observable<Map<String, List<FileInfo>>> getChangeRevisionFiles(
            @NonNull String changeId, @NonNull String revisionId) {
        return mService.getChangeRevisionFiles(changeId, revisionId);
    }

    @Override
    public Observable<Base64Data> getChangeRevisionFileContent(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String fileId) {
        return mService.getChangeRevisionFileContent(changeId, revisionId, fileId);
    }

    @Override
    public Observable<Response> getChangeRevisionFileDownload(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String fileId,
            @Nullable SuffixMode suffixMode, @Nullable Integer parent) {
        return mService.getChangeRevisionFileDownload(
                changeId, revisionId, fileId, suffixMode, parent);
    }

    @Override
    public Observable<DiffInfo> getChangeRevisionFileDiff(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String fileId, @Nullable Option intraline) {
        return mService.getChangeRevisionFileDiff(changeId, revisionId, fileId, intraline);
    }

    @Override
    public Observable<BlameInfo> getChangeRevisionFileBlame(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String fileId, @Nullable String base) {
        return mService.getChangeRevisionFileBlame(changeId, revisionId, fileId, base);
    }

    @Override
    public Observable<Void> setChangeRevisionFileAsReviewed(
            @NonNull String changeId, @NonNull String revisionId, @NonNull String fileId) {
        return mService.setChangeRevisionFileAsReviewed(changeId, revisionId, fileId);
    }

    @Override
    public Observable<Void> setChangeRevisionFileAsNotReviewed(
            @NonNull String changeId, @NonNull String revisionId, @NonNull String fileId) {
        return mService.setChangeRevisionFileAsNotReviewed(changeId, revisionId, fileId);
    }

    @Override
    public Observable<ChangeInfo> cherryPickChangeRevisionFile(@NonNull String changeId,
            @NonNull String revisionId, @NonNull String fileId, @NonNull CherryPickInput input) {
        return mService.cherryPickChangeRevisionFile(changeId, revisionId, fileId, input);
    }

    // ===============================
    // Gerrit configuration endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-config.html"
    // ===============================

    @Override
    public Observable<ServerVersion> getServerVersion() {
        return mService.getServerVersion();
    }

    @Override
    public Observable<ServerInfo> getServerInfo() {
        return mService.getServerInfo();
    }


    // ===============================
    // Gerrit groups endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-groups.html"
    // ===============================

    // ===============================
    // Gerrit plugins endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-plugins.html"
    // ===============================

    // ===============================
    // Gerrit projects endpoints
    // @link "https://gerrit-review.googlesource.com/Documentation/rest-api-projects.html"
    // ===============================

    @Override
    public Observable<Map<String, ProjectInfo>> getProjects(@Nullable Option showDescription,
            @Nullable Option showTree, @Nullable String branch,
            @Nullable ProjectType type, @Nullable String group) {
        return mService.getProjects(showDescription, showTree, branch, type, group);
    }

    @Override
    public Observable<ProjectInfo> getProject(@NonNull String name) {
        return mService.getProject(name);
    }

    @Override
    public Observable<ProjectInfo> createProject(@NonNull String name, @NonNull ProjectInput input) {
        return mService.createProject(name, input);
    }

    @Override
    public Observable<String> getProjectDescription(@NonNull String name) {
        return mService.getProjectDescription(name);
    }

    @Override
    public Observable<String> setProjectDescription(
            @NonNull String name, @NonNull ProjectDescriptionInput input) {
        return mService.setProjectDescription(name, input);
    }

    @Override
    public Observable<Void> deleteProjectDescription(@NonNull String name) {
        return mService.deleteProjectDescription(name);
    }

    @Override
    public Observable<String> getProjectParent(@NonNull String name) {
        return mService.getProjectParent(name);
    }

    @Override
    public Observable<String> setProjectParent(
            @NonNull String name, @NonNull ProjectParentInput input) {
        return mService.setProjectParent(name, input);
    }

    @Override
    public Observable<String> getProjectHead(@NonNull String name) {
        return mService.getProjectHead(name);
    }

    @Override
    public Observable<String> setProjectHead(@NonNull String name, @NonNull HeadInput input) {
        return mService.setProjectHead(name, input);
    }

    @Override
    public Observable<RepositoryStatisticsInfo> getProjectStatistics(@NonNull String name) {
        return mService.getProjectStatistics(name);
    }

    @Override
    public Observable<ConfigInfo> getProjectConfig(@NonNull String name) {
        return mService.getProjectConfig(name);
    }

    @Override
    public Observable<ConfigInfo> setProjectConfig(
            @NonNull String name, @NonNull ConfigInput input) {
        return mService.setProjectConfig(name, input);
    }

    @Override
    public Observable<Response> runProjectGc(@NonNull String name, @NonNull GcInput input) {
        return mService.runProjectGc(name, input);
    }
}
