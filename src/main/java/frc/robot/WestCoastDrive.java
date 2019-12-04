package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class WestCoastDrive extends Subsystem {
    CANSparkMax leftMaster, leftFront, leftRear, rightMaster, rightFront, rightRear;
    DifferentialDrive drive;
    CANPIDController leftPID, rightPID;
    AHRS _navX;

    double targetAngle;

    public WestCoastDrive(AHRS navX) {
        _navX = navX;
        _navX.getQuaternionZ();
        initDrive();
    }

    public void initDrive() {
        leftMaster = new CANSparkMax(11, MotorType.kBrushless);
        leftFront = new CANSparkMax(10, MotorType.kBrushless);
        leftRear = new CANSparkMax(12, MotorType.kBrushless);
        rightMaster = new CANSparkMax(14, MotorType.kBrushless);
        rightFront = new CANSparkMax(13, MotorType.kBrushless);
        rightRear = new CANSparkMax(15, MotorType.kBrushless);
        leftFront.follow(leftMaster);
        leftRear.follow(leftMaster);
        rightFront.follow(rightMaster);
        rightRear.follow(rightMaster);

        drive = new DifferentialDrive(leftMaster, rightMaster);
        drive.setSafetyEnabled(false);
        drive.setMaxOutput(0.5);

        resetNavX();
    }

    public void setToBrake() {
        leftMaster.setIdleMode(IdleMode.kBrake);
        rightMaster.setIdleMode(IdleMode.kBrake);
        leftRear.setIdleMode(IdleMode.kBrake);
        leftFront.setIdleMode(IdleMode.kBrake);
        rightRear.setIdleMode(IdleMode.kBrake);
        rightFront.setIdleMode(IdleMode.kBrake);
    }

    public void setToCoast() {
        leftMaster.setIdleMode(IdleMode.kCoast);
        rightMaster.setIdleMode(IdleMode.kCoast);
        leftRear.setIdleMode(IdleMode.kCoast);
        leftFront.setIdleMode(IdleMode.kCoast);
        rightRear.setIdleMode(IdleMode.kCoast);
        rightFront.setIdleMode(IdleMode.kCoast);
    }

    public void resetNavX() {
        _navX.reset();
    }

    public double getNavXAngle() {
        return _navX.getQuaternionZ();
    }

    public void drive(double speed, double rotation) {
        drive.curvatureDrive(speed, -rotation, true);
    }

    public void setLeftandRight(double left, double right) {
        leftMaster.set(-left);
        rightMaster.set(right);
    }

    public double getLeftEncoderValue() {
        return leftRear.getEncoder().getPosition();
    }

    public double getRightEncoderValue() {
        return rightMaster.getEncoder().getPosition();
    }

    public double getLeftEncoderInches() {
        return -(3.94 * Math.PI * (getLeftEncoderValue() / 6));
    }

    public double getRightEncoderInches() {
        return (3.94 * Math.PI * (getRightEncoderValue() / 6));
    }

    @Override
    protected void initDefaultCommand() {
    }
}
