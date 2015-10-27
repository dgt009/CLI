package model;


import org.postgis.Point;

/**
 * A Point on the globe, with longitude and latitude coordinates in SRID 4326.
 */
public class GeoPoint {
	private double lon;
	private double lat;

	/**
	 * Creates a GeoPoint from longitude and latitude.
	 * 
	 * @param lon
	 *            The longitude.
	 * @param lat
	 *            The latitude.
	 * @throws IllegalArgumentException
	 *             If lon is not in the range [-180, 180], or lat is not in the range [-90, 90].
	 */
	public GeoPoint(double lon, double lat) {
		if (lon > 180 || lon < -180) {
			throw new IllegalArgumentException("Longitude was " + lon + " but has to be in the range [-180, 180]");
		}
		if (lat > 90 || lat < -90) {
			throw new IllegalArgumentException("Latitude was " + lat + " but has to be in the range [-90, 90]");
		}
		this.lon = lon;
		this.lat = lat;
	}

	/**
	 * Creates a GeoPoint from a PostGIS {@link Point}. Assumes that the {@code x} member represents longitude.
	 * 
	 * @param postGisPoint
	 *            A PostGIS point.
	 * @throws NullPointerException
	 *             I {@code postGisPoint} is null.
	 */
	// TODO throw exception if p's srid isn't 4326? geopoint is supposed to be
	// implicitly 4326
	public GeoPoint(Point postGisPoint) {
		this(postGisPoint.getX(), postGisPoint.getY());
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		if (lon > 180 || lon < -180) {
			throw new IllegalArgumentException("Longitude was " + lon + " but has to be in the range [-180, 180]");
		}
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		if (lat > 90 || lat < -90) {
			throw new IllegalArgumentException("Latitude was " + lat + " but has to be in the range [-90, 90]");
		}
		this.lat = lat;
	}

	/**
	 * Converts this GeoPoint to a PostGIS Point with SRID 4326.
	 * 
	 * @return A PostGIS Point representing the same coordinates as this point.
	 */
	public Point toPostGisPoint() {
		Point p = new Point(lon, lat);
		p.setSrid(4326);
		return p;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof GeoPoint)) {
			return false;
		}
		GeoPoint point = (GeoPoint) o;
		return this.lon == point.lon && this.lat == point.lat;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + new Double(lon).hashCode();
		hash = 31 * hash + new Double(lat).hashCode();
		return hash;
	}

	@Override
	public String toString() {
		return "GeoPoint {lon: " + lon + ", lat: " + lat + "}";
	}
}
