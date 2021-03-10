package com.example.myapplicationmap;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final LatLng SEOUL = new LatLng(37.5536, 126.9696);
    private static final LatLng CHEONAN = new LatLng(36.8091, 127.1466);
    private static final LatLng CENTER = new LatLng(37.2401, 127.0956);
    private static final LatLng NEAR = new LatLng(36.8147, 127.1476);   // 천안역 근처 좌표 설정
    private Marker currentMarker = null;    // 가장 최근의 현재 위치 (마커로 현재 위치를 표시할 때 이용)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // 권한 요청

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    // 현재 위치에 따른 마커 이동을 처리하는 메소드
    public void setCurrentLocation(Location location) {
        if (currentMarker != null) currentMarker.remove(); // 현재 마커가 지도에 표시된 상태이면 지우기 (새로 갱신시키기 위해)

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude()); // 위치 받아오기

        MarkerOptions markerOptions = new MarkerOptions();  // 마커 옵션 설정
        markerOptions.position(currentLatLng);
        markerOptions.title("현재 위치");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.train));

        currentMarker = mMap.addMarker(markerOptions);  // 지도에 마커 추가
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);   // 현재 위치 표시 기능 이용

        // 서울역에서 천안역을 잇는 선 그리기
        mMap.addPolyline(new PolylineOptions().add(SEOUL, CHEONAN));

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);  // 로케이션 매니저 선언

            // 위치가 바뀌면 실행될 로케이션 리스너
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

                // 천안역과 가까워진 일정 범위 내로 currentPosition이 변경되면
                if (((NEAR.latitude >= currentPosition.latitude) && (currentPosition.latitude >= CHEONAN.latitude) == true) || (((NEAR.longitude >= currentPosition.longitude) && (currentPosition.longitude >= CHEONAN.longitude)) == true))
                {
                    ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);    // 알람음 설정
                    tone.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 300);    // 3초 동안 알람음 재생
                    Toast.makeText(MapsActivity.this, "천안역에 도착했습니다!", Toast.LENGTH_LONG).show();    // 토스트 메시지 띄우기
                }

                // 현재 위치에 따라 마커 이동
                setCurrentLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // 마커 설정
        Marker mSeoul = mMap.addMarker(new MarkerOptions().position(SEOUL).title("출발지 - 서울역").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1)));
        mSeoul.setTag(0);
        Marker mCheonan = mMap.addMarker(new MarkerOptions().position(CHEONAN).title("도착지 - 천안역").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
        mCheonan.setTag(0);

        // 카메라 설정 (선이 잘 보이도록 직선 거리 중앙 정도의 좌표 이용)
        mMap.moveCamera(CameraUpdateFactory.newLatLng((CENTER)));
        // 카메라 줌 정도 설정
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
        // 카메라 애니메이션 설정
        mMap.animateCamera(zoom);
    }
}
