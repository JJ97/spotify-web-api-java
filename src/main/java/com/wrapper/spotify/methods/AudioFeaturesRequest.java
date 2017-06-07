package com.wrapper.spotify.methods;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.AudioFeature;
import net.sf.json.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class AudioFeaturesRequest extends AbstractRequest {


    public AudioFeaturesRequest(Builder builder) {
        super(builder);
    }

    public SettableFuture<List<AudioFeature>> getAsync() {
        SettableFuture<List<AudioFeature>> audioFeaturesFuture = SettableFuture.create();

        try {
            String jsonString = getJson();
            audioFeaturesFuture.set(createAudioFeatures(jsonString));
        } catch (Exception e) {
            audioFeaturesFuture.setException(e);
        }

        return audioFeaturesFuture;
    }

    public List<AudioFeature> get() throws IOException, WebApiException {
        String jsonString = getJson();

        return createAudioFeatures(jsonString);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        public Builder id(List<String> ids) {
          assert (ids != null);
          String idsParameter = Joiner.on(",").join(ids).toString();
          path("/v1/audio-features");
          return parameter("ids", idsParameter);
        }

        public AudioFeaturesRequest build() {
            return new AudioFeaturesRequest(this);
        }

    }


    private static AudioFeature createAudioFeature(JSONObject audioFeatureJson) {
        if (audioFeatureJson == null || audioFeatureJson.isNullObject()) {
            return null;
        }

        AudioFeature audioFeature = new AudioFeature();
        audioFeature.setDanceability(audioFeatureJson.getDouble("danceability"));
        audioFeature.setEnergy(audioFeatureJson.getDouble("energy"));
        audioFeature.setKey(audioFeatureJson.getInt("key"));
        audioFeature.setLoudness(audioFeatureJson.getDouble("loudness"));
        audioFeature.setMode(audioFeatureJson.getInt("mode"));
        audioFeature.setSpeechiness(audioFeatureJson.getDouble("speechiness"));
        audioFeature.setAcousticness(audioFeatureJson.getDouble("acousticness"));
        audioFeature.setInstrumentalness(audioFeatureJson.getDouble("instrumentalness"));
        audioFeature.setLiveness(audioFeatureJson.getDouble("liveness"));
        audioFeature.setValence(audioFeatureJson.getDouble("valence"));
        audioFeature.setTempo(audioFeatureJson.getDouble("tempo"));
        audioFeature.setType(audioFeatureJson.getString("type"));
        audioFeature.setId(audioFeatureJson.getString("id"));
        audioFeature.setUri(audioFeatureJson.getString("uri"));
        audioFeature.setTrackHref(audioFeatureJson.getString("track_href"));
        audioFeature.setAnalysisUrl(audioFeatureJson.getString("analysis_url"));
        audioFeature.setDurationMs(audioFeatureJson.getInt("duration_ms"));
        audioFeature.setTimeSignature(audioFeatureJson.getInt("time_signature"));


        return audioFeature;
    }

    private static List<AudioFeature> createAudioFeatures(String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray audioFeaturesJsonArray = jsonObject.getJSONArray("audio_features");
        return createAudioFeatures(audioFeaturesJsonArray);

    }

    private static List<AudioFeature> createAudioFeatures(JSONArray jsonArray) {
        List<AudioFeature> returnedAudioFeatures = new ArrayList<AudioFeature>();
        for (int i = 0; i < jsonArray.size(); i++) {
          returnedAudioFeatures.add(createAudioFeature(jsonArray.getJSONObject(i)));
        }
        return returnedAudioFeatures;
    }

}
