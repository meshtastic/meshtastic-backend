package org.meshtastic.backend.rest


import org.geojson.Feature
import org.geojson.FeatureCollection
import org.geojson.Point
import org.meshtastic.backend.model.NodeDB
import org.meshtastic.common.model.Position
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
A Controller that returns various endpoints of GeoJSON, formatted to be easily passable to Leaflet.js.

Per https://leafletjs.com/examples/geojson/
Leaflet likes feature collections of features like this

"type": "Feature",
"properties": {
"name": "Coors Field",
"amenity": "Baseball Stadium",
"popupContent": "This is where the Rockies play!"
},
"geometry": {
"type": "Point",
"coordinates": [-104.99404, 39.75621]
}
 */
@RestController
class GeoJSONController(private val nodes: NodeDB) {

    @RequestMapping("$apiPrefix/geoJSON/nodes")
    fun getNodes(): FeatureCollection {
        val fc = FeatureCollection()

        val positionNodes = nodes.db.values.mapNotNull {
            val p = it.position?.let { proto -> Position(proto) }
            if (p != null && p.latitude != 0.0 && p.longitude != 0.0) {
                val f = Feature()

                f.setProperty("id", it.id)

                val u = it.user
                if (u != null)
                    f.setProperty("longName", u.longName)
                f.setProperty("lastHeard", it.lastHeard)
                f.setProperty("position", it.position)
                f.setProperty("user", it.user)
                f.geometry = Point(p.longitude, p.latitude)
                f
            } else
                null // Skip - no position found
        }

        fc.addAll(positionNodes)
        return fc
    }
}