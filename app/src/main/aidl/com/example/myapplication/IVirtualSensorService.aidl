// IVirtualSensorService.aidl
package com.example.myapplication;

// Declare any non-default types here with import statements

interface IVirtualSensorService {
  int getSensorValue();
  void setSensorValue(int value);
}