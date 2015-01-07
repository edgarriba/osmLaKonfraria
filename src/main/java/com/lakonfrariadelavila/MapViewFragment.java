package com.lakonfrariadelavila;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by eriba on 2/01/15.
 */
public class MapViewFragment extends Fragment {

    // Location attributes
    private GeoPoint startPoint;
    private GeoPoint endPoint;
    private ImageButton imgButton;

    // Map View attributes
    private View view;
    private MapView mMapView;
    private IMapController mIMapController;

    // Default constructor
    public static MapViewFragment newInstance() {
        MapViewFragment fragment = new MapViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mIMapController = mMapView.getController();
        mIMapController.setZoom(10);
        //mIMapController.setCenter(startPoint);

        //Toast.makeText(getActivity(), "onCreateView(MapViewFragment)", Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Initialise location button
        imgButton = (ImageButton) view.findViewById(R.id.imgButtonLocation);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerMapLocation( 16 );
                Toast.makeText(getActivity(),R.string.toast_search_location, Toast.LENGTH_SHORT).show();

                // ADD A MARKER
                Marker startMarker = new Marker(mMapView);
                startMarker.setPosition( startPoint );
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                startMarker.setTitle("Start point");
                //startMarker.setIcon(getResources().getDrawable(R.drawable.marker_kml_point).mutate());
                //startMarker.setImage(getResources().getDrawable(R.drawable.ic_launcher));
                //startMarker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, map));
                startMarker.setDraggable(true);
                //startMarker.setOnMarkerDragListener(new OnMarkerDragListenerDrawer());
                mMapView.getOverlays().add(startMarker);

            }
        });

        // Initialise map view
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setUseDataConnection(true);
        mMapView.setMultiTouchControls(true);

        // Initialise location (LA PALMA)
        startPoint = new GeoPoint(41.41290, 1.96943);

        // Initialise location (FRANCE?)
        endPoint = new GeoPoint(48.13, -1.63);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "onResume(MapViewFragment)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "onPause(MapViewFragment)", Toast.LENGTH_SHORT).show();
    }

    public void centerMapLocation( Integer zoomLevel) {
        mMapView.getController().setZoom( zoomLevel );
        mMapView.getController().animateTo( startPoint );
    }


}