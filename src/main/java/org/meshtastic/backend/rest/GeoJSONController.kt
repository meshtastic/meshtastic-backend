package org.meshtastic.backend.rest


import org.geojson.Feature
import org.geojson.FeatureCollection
import org.geojson.Point
import org.meshtastic.backend.model.NodeDB
import org.meshtastic.common.model.Position
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.servlet.http.HttpServletResponse

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

    // RFC1123 date per https://stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java
    private val dateFormat =
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).withZone(ZoneId.of("GMT"))

    @RequestMapping("$apiPrefix/geoJSON/nodes")
    @ResponseBody
    fun getNodes(response: HttpServletResponse): FeatureCollection {
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

        // permissive CORS
        response.setHeader("Access-Control-Allow-Origin", "*")

        val now = ZonedDateTime.now()
        response.setHeader("Last-Modified", dateFormat.format(now))

        // Forbid caching (for now)
        response.setHeader("Cache-Control", "no-store"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Expires", "0"); // Proxies.
        return fc
    }
}