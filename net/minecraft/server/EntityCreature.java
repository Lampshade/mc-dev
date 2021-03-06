package net.minecraft.server;

public class EntityCreature extends EntityLiving {

    private PathEntity pathEntity;
    protected Entity target;
    protected boolean e = false;

    public EntityCreature(World world) {
        super(world);
    }

    protected boolean w() {
        return false;
    }

    protected void c_() {
        this.e = this.w();
        float f = 16.0F;

        if (this.target == null) {
            this.target = this.findTarget();
            if (this.target != null) {
                this.pathEntity = this.world.findPath(this, this.target, f);
            }
        } else if (!this.target.S()) {
            this.target = null;
        } else {
            float f1 = this.target.f(this);

            if (this.e(this.target)) {
                this.a(this.target, f1);
            } else {
                this.b(this.target, f1);
            }
        }

        if (!this.e && this.target != null && (this.pathEntity == null || this.random.nextInt(20) == 0)) {
            this.pathEntity = this.world.findPath(this, this.target, f);
        } else if (!this.e && (this.pathEntity == null && this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
            boolean flag = false;
            int i = -1;
            int j = -1;
            int k = -1;
            float f2 = -99999.0F;

            for (int l = 0; l < 10; ++l) {
                int i1 = MathHelper.floor(this.locX + (double) this.random.nextInt(13) - 6.0D);
                int j1 = MathHelper.floor(this.locY + (double) this.random.nextInt(7) - 3.0D);
                int k1 = MathHelper.floor(this.locZ + (double) this.random.nextInt(13) - 6.0D);
                float f3 = this.a(i1, j1, k1);

                if (f3 > f2) {
                    f2 = f3;
                    i = i1;
                    j = j1;
                    k = k1;
                    flag = true;
                }
            }

            if (flag) {
                this.pathEntity = this.world.a(this, i, j, k, 10.0F);
            }
        }

        int l1 = MathHelper.floor(this.boundingBox.b + 0.5D);
        boolean flag1 = this.ac();
        boolean flag2 = this.ad();

        this.pitch = 0.0F;
        if (this.pathEntity != null && this.random.nextInt(100) != 0) {
            Vec3D vec3d = this.pathEntity.a(this);
            double d0 = (double) (this.length * 2.0F);

            while (vec3d != null && vec3d.d(this.locX, vec3d.b, this.locZ) < d0 * d0) {
                this.pathEntity.a();
                if (this.pathEntity.b()) {
                    vec3d = null;
                    this.pathEntity = null;
                } else {
                    vec3d = this.pathEntity.a(this);
                }
            }

            this.aC = false;
            if (vec3d != null) {
                double d1 = vec3d.a - this.locX;
                double d2 = vec3d.c - this.locZ;
                double d3 = vec3d.b - (double) l1;
                float f4 = (float) (Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f5 = f4 - this.yaw;

                for (this.aA = this.aE; f5 < -180.0F; f5 += 360.0F) {
                    ;
                }

                while (f5 >= 180.0F) {
                    f5 -= 360.0F;
                }

                if (f5 > 30.0F) {
                    f5 = 30.0F;
                }

                if (f5 < -30.0F) {
                    f5 = -30.0F;
                }

                this.yaw += f5;
                if (this.e && this.target != null) {
                    double d4 = this.target.locX - this.locX;
                    double d5 = this.target.locZ - this.locZ;
                    float f6 = this.yaw;

                    this.yaw = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                    f5 = (f6 - this.yaw + 90.0F) * 3.1415927F / 180.0F;
                    this.az = -MathHelper.sin(f5) * this.aA * 1.0F;
                    this.aA = MathHelper.cos(f5) * this.aA * 1.0F;
                }

                if (d3 > 0.0D) {
                    this.aC = true;
                }
            }

            if (this.target != null) {
                this.a(this.target, 30.0F, 30.0F);
            }

            if (this.positionChanged && !this.B()) {
                this.aC = true;
            }

            if (this.random.nextFloat() < 0.8F && (flag1 || flag2)) {
                this.aC = true;
            }
        } else {
            super.c_();
            this.pathEntity = null;
        }
    }

    protected void a(Entity entity, float f) {}

    protected void b(Entity entity, float f) {}

    protected float a(int i, int j, int k) {
        return 0.0F;
    }

    protected Entity findTarget() {
        return null;
    }

    public boolean d() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.boundingBox.b);
        int k = MathHelper.floor(this.locZ);

        return super.d() && this.a(i, j, k) >= 0.0F;
    }

    public boolean B() {
        return this.pathEntity != null;
    }

    public void a(PathEntity pathentity) {
        this.pathEntity = pathentity;
    }

    public Entity E() {
        return this.target;
    }

    public void c(Entity entity) {
        this.target = entity;
    }
}
