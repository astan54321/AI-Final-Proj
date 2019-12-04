/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  WestCoastDrive drive;
  // AHRS navx;
  AHRS navx = new AHRS(Port.kMXP);
  Joystick joy;
  
  @Override
  public void robotInit() {
    navx.reset();
    drive = new WestCoastDrive(navx);
    joy = new Joystick(0);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    navx.reset();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("ANGLE: ", navx.getAngle ());
  }

  double target = 180.0;
  double kP = 1.5/180.0;
  double kD = 0.28*9.0;
  double distance = 0;
  double speed = 0;
  
  @Override
  public void teleopPeriodic() {
    double current = navx.getAngle();
    double newDist = target-current;
    speed = distance - newDist;
    if (speed > 0.01)
      System.out.println(speed);
    distance = newDist;
    double speed = joy.getRawAxis(1);
    double rotation = ((distance * kP)) - (speed * kD);
    drive.drive(speed, rotation);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
