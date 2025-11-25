public class Plot {

    private Crop crop;

    public Plot() {
        this.crop = null;
    }

    public void plant(Crop crop) {
        if (crop == null) {
            throw new IllegalArgumentException("Cannot plant null crop.");
        }
        
        if (!isEmpty()) {
            throw new IllegalStateException("Plot is already occupied.");
        }

        this.crop = crop;
    }

    public void removeCrop() {
        this.crop = null;
    }

    public boolean hasDeadCrop() {
        if (!isEmpty()) {
            return crop.isDead();
        }
        return false;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "E";
        } else {
            return crop.toString();
        }
    }
    
    public Crop getCrop() {
        return crop;
    }
}