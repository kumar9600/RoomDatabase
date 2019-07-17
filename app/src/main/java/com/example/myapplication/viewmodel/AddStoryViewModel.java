package com.example.myapplication.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.adapter.Adapter;
import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.model.StoriesDbModel;

import java.util.List;

public class AddStoryViewModel extends AndroidViewModel {

    private static final String TAG = AddStoryViewModel.class.getSimpleName();
    private AppDatabase appDatabase;
    private final LiveData<List<StoriesDbModel>> storiesList;

    public AddStoryViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        storiesList = appDatabase.storyDao().getAllStories();
    }

    public void getSingleStory(final StoriesDbModel dbModel) {
        new getSingleStoryAsyncTask(appDatabase).execute(dbModel);
    }

    public LiveData<List<StoriesDbModel>> getAllStoriesList() {
        return storiesList;
    }

    public void addStory(final StoriesDbModel storiesDbModel) {
        new addAsyncTask(appDatabase).execute(storiesDbModel);
    }

    public void deleteStory(Activity context, final StoriesDbModel storiesDbModel, Adapter adapter, int position, List<StoriesDbModel> modelList) {
        new DeleteAsyncTask(context, appDatabase, adapter, position, modelList).execute(storiesDbModel);
    }

    private static class getSingleStoryAsyncTask extends AsyncTask<StoriesDbModel, Void, String> {

        private AppDatabase db;

        public getSingleStoryAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected String doInBackground(StoriesDbModel... dbModels) {
            String data = "";
            StoriesDbModel borrowModel = dbModels[0];
            String itemName = borrowModel.getShortDesc();
            StoriesDbModel model = db.storyDao().getItembyId(itemName);
            if (!model.getShortDesc().isEmpty()) {
                data = model.getShortDesc();
            }
            Log.e(TAG, "Single story data : " + data);
            return data;
        }
    }

    private static class addAsyncTask extends AsyncTask<StoriesDbModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(StoriesDbModel... storiesDbModels) {
            db.storyDao().addStory(storiesDbModels[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<StoriesDbModel, Void, Void> {

        private Activity context;
        private AppDatabase database;
        private Adapter adapter;
        private int position;
        private List<StoriesDbModel> modelList;

        DeleteAsyncTask(Activity context, AppDatabase database, Adapter adapter, int position, List<StoriesDbModel> modelList) {
            this.context = context;
            this.database = database;
            this.adapter = adapter;
            this.position = position;
            this.modelList = modelList;
        }

        @Override
        protected Void doInBackground(StoriesDbModel... storiesDbModels) {

            database.
                    storyDao().
                    deleteStory(storiesDbModels[0]);

            context.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemRemoved(position);
                            modelList.remove(position);
                            int size = modelList.size();
                            adapter.notifyItemRangeChanged(position, size);
                            Toast.makeText(context, "item removed", Toast.LENGTH_SHORT).show();
                        }
                    });
            return null;
        }
    }
}
