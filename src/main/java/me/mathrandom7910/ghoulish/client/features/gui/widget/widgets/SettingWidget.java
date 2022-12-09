package me.mathrandom7910.ghoulish.client.features.gui.widget.widgets;

import me.mathrandom7910.ghoulish.client.features.gui.ClickGui;
import me.mathrandom7910.ghoulish.client.features.gui.widget.GuiWidget;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BindSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.NumSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.floatingpoint.FloatingPointSetting;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.MouseData;
import me.mathrandom7910.ghoulish.client.util.KeyUtil;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.util.math.MatrixStack;

public class SettingWidget extends GuiWidget<SettingWidget> implements MCInst {
    private final Setting<?> setting;
    private int lChild;
    private final GuiWidget<SettingWidget> parent;
    boolean settingNum;
    private double mx;
    private double my;
    private long timeOver = 0;

    public SettingWidget(Setting<?> setting, GuiWidget<SettingWidget> parent) {
        super(setting.getName());
        this.setting = setting;
        this.parent = parent;
    }

    @Override
    public void render(GuiWidget<?> parent, int largestChild, MatrixStack stack, MouseData mouseData) {
        RenderUtil2d.drawBox(stack, getPos(), largestChild, RenderUtil2d.FONT_HEIGHT, guiModule.GUI_COLOR.getColor());

        if(mx >= getPos().getX() && mx <= getPos().getX() + lChild && my >= getPos().getY() && my <= getPos().getY() + RenderUtil2d.FONT_HEIGHT && (ClickGui.renderingDesc == null || ClickGui.renderingDesc == this)) {

            if(System.currentTimeMillis() > timeOver) {
                ClickGui.renderingDesc = this;
                RenderUtil2d.drawText(stack, setting.getDesc(), getPos().add(largestChild + guiModule.SIDE_BUFFER.get()), guiModule.GUI_TEXT_COLOR.getColor());
            }
        } else {
            if(ClickGui.renderingDesc == this) {
                ClickGui.renderingDesc = null;
            }
            timeOver = (System.currentTimeMillis() + guiModule.DESC_DELAY.get());
        }

//        Color boxCol = guiModule.GUI_COLOR.getColor();
        if (setting instanceof BoolSetting && ((BoolSetting) setting).get()) {
            RenderUtil2d.drawBox(stack, getPos(), largestChild, RenderUtil2d.FONT_HEIGHT, guiModule.GUI_ENABLED.getColor());
        }


        String displayName = getName();

        if (setting instanceof NumSetting<?> numSetting) {
            displayName += " : " + setting.get();

            double addVal = numSetting.getMin().longValue() * -1;
            double percentage = (numSetting.get().doubleValue() + addVal) / (addVal + numSetting.getMax().doubleValue());
            RenderUtil2d.drawBox(stack, getPos(), (int) (lChild * percentage), RenderUtil2d.FONT_HEIGHT, guiModule.GUI_ENABLED.getColor());

        } else if(setting instanceof BindSetting) {
            if(ModuleManager.getBindingModule() == setting.getMod()) {
                displayName += " : BINDING";
            } else {
                displayName += " : " + KeyUtil.getKeyName(setting.getMod().getBind().get());
            }
        } else if(setting instanceof EnumSetting<?> enumSetting) {
            displayName += " : " + enumSetting.get().name();
        }
        parent.setLargest(displayName);
        RenderUtil2d.drawText(stack, displayName, getPos().add((largestChild - RenderUtil2d.measureText(displayName)) / 2), guiModule.GUI_TEXT_COLOR.getColor());
        lChild = largestChild;

    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        if (mouseX >= getPos().getX() && mouseX <= getPos().getX() + lChild && mouseY >= getPos().getY() && mouseY <= getPos().getY() + RenderUtil2d.FONT_HEIGHT) {
            if (button == 0) {
                if (setting instanceof BoolSetting boolSetting) {
                    if(boolSetting == setting.getMod().getEnabled()) {
                        setting.getMod().toggle();
                    } else {
                        boolSetting.set(!boolSetting.get());
                    }
                } else if (setting instanceof BindSetting) {
                    if (ModuleManager.getBindingModule() == null) {
                        if(mc.currentScreen instanceof ClickGui) {
                            mc.currentScreen.close();
                        }
                        ModuleManager.setBindingModule(setting.getMod());
                    } else if (ModuleManager.getBindingModule() == setting.getMod()) {
                        ModuleManager.setBindingModule(null);
                    }
                } else if (setting instanceof NumSetting<?> numSetting) {
                    settingNum = true;
//                    double perc = (mouseX - getPos().getX()) /;
                    double percentage = (mouseX - getPos().getX()) / (double) lChild;
                    if (numSetting instanceof FloatingPointSetting<?> floatingPointSetting) {
                        double val = (percentage * (numSetting.getMax().doubleValue() - numSetting.getMin().doubleValue())) + numSetting.getMin().doubleValue();
                        floatingPointSetting.setDouble(val);
                    } else {
                        long val = (long) ((percentage * (numSetting.getMax().longValue() - numSetting.getMin().longValue())) + numSetting.getMin().longValue());
                        numSetting.setLong(val);
                    }
                } else if (setting instanceof EnumSetting<?> enumSetting) {
                    enumSetting.next();
                }
            } else if (button == 1) {
                if(setting instanceof EnumSetting<?> enumSetting) {
                    enumSetting.prev();
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onMouseUp(double mouseX, double mouseY, int button) {
        boolean sN = settingNum;
        settingNum = false;
        return sN;
    }

    @Override
    public boolean onMouseMove(double mouseX, double mouseY) {
        mx = mouseX;
        my = mouseY;
        if(settingNum) {
            NumSetting<?> numSetting = (NumSetting<?>) setting;
            double percentage = (mouseX - getPos().getX()) / (double) lChild;
            if (numSetting instanceof FloatingPointSetting<?> floatingPointSetting) {
                double val = (percentage * (numSetting.getMax().doubleValue() - numSetting.getMin().doubleValue())) + numSetting.getMin().doubleValue();
                floatingPointSetting.setDouble(val);
            } else {
                long val = Math.round((percentage * (numSetting.getMax().longValue() - numSetting.getMin().longValue())) + numSetting.getMin().longValue());
                numSetting.setLong(val);
            }
            return true;
        }

        return false;
    }

    public Setting<?> getSetting() {
        return setting;
    }
}
