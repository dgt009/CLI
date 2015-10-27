/** 
 * Copyright (C) Maritime Data Systems, GmbH - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by dhruvil, Oct 26, 2015
 */

package model;

import java.sql.Timestamp;

public class Shipyard {

	private GeoPoint position;
	private int imo;
	private String vessel_name;
	private Timestamp ts;
	private String shipyard_name;

	public GeoPoint getPosition() {
		return position;
	}

	public void setPosition(GeoPoint position) {
		this.position = position;
	}

	public int getImo() {
		return imo;
	}

	public void setImo(int imo) {
		this.imo = imo;
	}

	public String getVessel_name() {
		return vessel_name;
	}

	public void setVessel_name(String vessel_name) {
		this.vessel_name = vessel_name;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public String getShipyard_name() {
		return shipyard_name;
	}

	public void setShipyard_name(String shipyard_name) {
		this.shipyard_name = shipyard_name;
	}

}
