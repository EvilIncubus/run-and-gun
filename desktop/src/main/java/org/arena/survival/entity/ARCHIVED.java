package org.arena.survival.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import org.arena.survival.assets.Assets;

/**
 * Player — игрок в виде квадрата с поворотом к курсору и движением по WASD.
 * Здесь реализованы:
 * - движение с нормализацией по диагонали
 * - поворот к курсору
 * - ограничение по границам мира
 * - отрисовка квадрата с ShapeRenderer
 */
public class ARCHIVED {
//
//    // --------------------- Поля ---------------------
//
//    /** Позиция игрока (нижний левый угол квадрата) */
//    private final Vector2 position;
//
//    /** Размер игрока */
//    private final float size;
//
//    /** Скорость движения игрока */
//    private final float speed;
//
//    /** Угол поворота игрока в градусах */
//    private float rotation = 0f;
//
//    /** Камера для перевода координат мыши */
//    private final OrthographicCamera camera;
//
//    /** Рендерер для рисования игрока */
//    private final ShapeRenderer shapeRenderer;
//
//    /** Размер мира (ширина) */
//    private final float worldWidth;
//
//    /** Размер мира (высота) */
//    private final float worldHeight;
//
//    /**
//     * Все активные пули игрока
//     */
//    private Array<Bullet> bullets = new Array<>();
//
//    // время между выстрелами
//    private float shootCooldown = 0.2f; // 5 выстрелов в секунду
//
//    // таймер до следующего выстрела
//    private float shootTimer = 0f;
//
//    private int health = 5; // жизней у игрока
//
//    private float damageCooldown = 1f; // 1 секунда между уронами
//
//    private float damageTimer = 0f;    // таймер для отслеживания кулдауна
//
//    private boolean usingController = false; // джойстик подключён
//
//    private Sound shootSound;
//
//    private Texture texture;
//
//    private SpriteBatch batch;
//    /**
//     * Конструктор игрока
//     * @param x начальная X позиция
//     * @param y начальная Y позиция
//     * @param size размер квадрата игрока
//     * @param speed скорость движения
//     * @param camera камера для unproject()
//     * @param shapeRenderer ShapeRenderer для отрисовки
//     * @param worldWidth ширина игрового мира
//     * @param worldHeight высота игрового мира
//     */
//    public Player(float x, float y, float size, float speed,
//                  OrthographicCamera camera, ShapeRenderer shapeRenderer,
//                  float worldWidth, float worldHeight, SpriteBatch batch) {
//        this.position = new Vector2(x, y);
//        this.size = size;
//        this.speed = speed;
//        this.camera = camera;
//        this.shapeRenderer = shapeRenderer;
//        this.worldWidth = worldWidth;
//        this.worldHeight = worldHeight;
//        this.batch = batch;
//        shootSound = Assets.shootSound;
//        texture = Assets.player;
//    }
//
//    // --------------------- Логика ---------------------
//
//    public void moveByJoystick(float x, float y) {
//        // маленькая мёртвая зона, чтобы случайный дребезг оси не двигал игрока
//        if (Math.abs(x) < 0.2f) x = 0;
//        if (Math.abs(y) < 0.2f) y = 0;
//
//        Vector2 dir = new Vector2(x, -y); // y ось обычно инвертирована
//        if (dir.len() > 0) {
//            dir.nor();
//            position.mulAdd(dir, speed * Gdx.graphics.getDeltaTime());
//            clampPlayerToWorld();
//        }
//    }
//
//    public void aimByJoystick(float x, float y) {
//        if (Math.abs(x) < 0.2f && Math.abs(y) < 0.2f) return;
//
//        Vector2 dir = new Vector2(x, -y);
//        rotation = dir.angleDeg();
//    }
//
//    public void shootByController() {
//        // Ограничение по скорости стрельбы
//        if (shootTimer <= 0f) {
//            Vector2 spawn = new Vector2(getCenterX(), getCenterY());
//            Vector2 dir = new Vector2((float) Math.cos(Math.toRadians(rotation)), (float)Math.sin(Math.toRadians(rotation)));
//            bullets.add(new Bullet(spawn, dir));
//            shootSound.play(0.5f);
//            shootTimer = shootCooldown;
//        }
//    }
//
//    /**
//     * update() — обновление логики игры
//     * @param delta Время с прошлого кадра (сек)
//     */
//    public void update(float delta, Controller controller) {
//
//        if (usingController && controller != null) {
//            // движение - левая ось (обычно axis 0 и 1)
//            float moveX = controller.getAxis(0); // -1..1
//            float moveY = controller.getAxis(1); // -1..1
//            moveByJoystick(moveX, moveY);
//
//            // прицел - правая ось (обычно axis 2 и 3)
//            float aimX = controller.getAxis(2);
//            float aimY = controller.getAxis(3);
//            aimByJoystick(aimX, aimY);
//
//            if (controller.getButton(0)) {
//                System.out.println("Shoot");
//                shootByController();
//            }
//        } else {
//            handleInput(delta);       // обработка ввода игрока
//            updateRotation();         // обновление угла поворота к курсору
//            shootFromMouse();
//        }
//
//        // уменьшаем таймер стрельбы
//        §§§§§§§§§§§§§§ -= delta;
//
//        // уменьшаем таймер кулдауна
//        if (damageTimer > 0) {
//            damageTimer -= delta;
//        }
//
//        clampPlayerToWorld();     // ограничение игрока границами мира
//
//    }
//
//    private void shootFromMouse(){
//        // если нажата левая кнопка мыши
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && shootTimer <= 0) {
//            shootSound.play(0.5f);
//            Vector3 mouse3 = new Vector3(
//                    Gdx.input.getX(),
//                    Gdx.input.getY(),
//                    0
//            );
//
//            // перевод экранных координат в координаты мира
//            camera.unproject(mouse3);
//
//            // теперь создаём Vector2
//            Vector2 mouse = new Vector2(mouse3.x, mouse3.y);
//
//            float centerX = position.x + size / 2f;
//            float centerY = position.y + size / 2f;
//
//            Vector2 direction = new Vector2(
//                    mouse.x - centerX,
//                    mouse.y - centerY
//            );
//
//            Vector2 spawn = new Vector2(centerX, centerY);
//
//            shootTimer = shootCooldown;
//
//            bullets.add(new Bullet(spawn, direction));
//        }
//    }
//
//    // --------------------- Логика движения ---------------------
//
//    /**
//     * handleInput — движение игрока по клавишам WASD
//     * Используется нормализация, чтобы диагонали не были быстрее
//     * @param delta Время с прошлого кадра
//     */
//    private void handleInput(float delta) {
//        float x = 0;
//        float y = 0;
//
//        // Определяем направление движения
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += 1;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= 1;
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= 1;
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += 1;
//
//        // Создаём вектор направления
//        Vector2 direction = new Vector2(x, y);
//
//        // Нормализуем, чтобы скорость была одинаковой по диагонали
//        if (direction.len() > 0) {
//            direction.nor();
//            // Обновляем позицию игрока
//            position.mulAdd(direction, speed * delta);
//        }
//    }
//
//    /**
//     * clampPlayerToWorld — ограничение позиции игрока границами мира
//     */
//    private void clampPlayerToWorld() {
//        position.x = Math.max(0, Math.min(worldWidth - size, position.x));
//        position.y = Math.max(0, Math.min(worldHeight - size, position.y));
//    }
//
//    /**
//     * Обновляет угол поворота игрока в сторону курсора мыши.
//     *
//     * Логика:
//     * 1. Получаем позицию мыши в экранных координатах
//     * 2. Переводим её в координаты игрового мира (camera.unproject)
//     * 3. Строим вектор направления от игрока к курсору
//     * 4. Вычисляем угол этого вектора
//     */
//    private void updateRotation() {
//        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//        camera.unproject(mouse);
//
//        float centerX = position.x + size / 2f;
//        float centerY = position.y + size / 2f;
//
//        Vector2 dir = new Vector2(mouse.x - centerX, mouse.y - centerY);
//        rotation = dir.angleDeg();
//    }
//
//    // --------------------- Рендер ---------------------
//
//    /**
//     * Отрисовка игрока
//     */
//    public void render() {
//
//        batch.begin();
//        batch.draw(
//                texture,
//                position.x,
//                position.y,
//                size / 2f,
//                size / 2f,
//                size,
//                size,
//                1f,
//                1f,
//                rotation,
//                0,
//                0,
//                texture.getWidth(),
//                texture.getHeight(),
//                false,
//                false
//        );
//        batch.end();
//
//        // рисуем пули
//        for (Bullet bullet : bullets) {
//            bullet.render(shapeRenderer);
//        }
//    }
//
//    // --------------------- Геттеры ---------------------
//
//    /** Центр X (для стрельбы, коллизий и т.д.) */
//    public float getCenterX() { return position.x + size / 2f; }
//
//    /** Центр Y (для стрельбы, коллизий и т.д.) */
//    public float getCenterY() { return position.y + size / 2f; }
//
//    public Array<Bullet> getBullets() {
//        return bullets;
//    }
//
//    public boolean takeDamageAndIsDead(int damage) {
//        if (damageTimer <= 0) {
//            health -= damage;
//            damageTimer = damageCooldown; // сброс таймера кулдауна
//            System.out.println("Player hit! Health: " + health);
//
//            if (health <= 0) {
//                return true;
//                // здесь можно добавить логику перезапуска уровня
//            }
//        }
//        return false;
//    }
//
//    public int getHealth() {
//        return health;
//    }
//
//    public float getSize() {
//        return size;
//    }
//
//    public Vector2 getPosition() {
//        return position;
//    }
//
//    public void setUsingController(boolean usingController) {
//        this.usingController = usingController;
//    }
//
//    public void dispose() {
//        if (shootSound != null) shootSound.dispose();
//    }
}

