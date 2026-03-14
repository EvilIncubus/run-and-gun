package org.arena.survival.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapSystem {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private TiledMapTileLayer wallLayer;

    private int tileWidth;
    private int tileHeight;

    public MapSystem(TiledMap map) {

        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(map);

        // получаем слой стен ОДИН РАЗ
        wallLayer = (TiledMapTileLayer) map.getLayers().get("Tile Layer 2");

        tileWidth = wallLayer.getTileWidth();
        tileHeight = wallLayer.getTileHeight();
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public boolean isWall(float worldX, float worldY) {

        int tileX = (int)(worldX / tileWidth);
        int tileY = (int)(worldY / tileHeight);

        TiledMapTileLayer.Cell cell = wallLayer.getCell(tileX, tileY);

        return cell != null;
    }

}
