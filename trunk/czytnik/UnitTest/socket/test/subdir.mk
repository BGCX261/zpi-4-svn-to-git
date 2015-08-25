################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../socket/test/SocketClientTest.cpp 

OBJS += \
./socket/test/SocketClientTest.o 

CPP_DEPS += \
./socket/test/SocketClientTest.d 


# Each subdirectory must supply rules for building sources it contributes
socket/test/%.o: ../socket/test/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -DUNIT_TEST -I/usr/include/libfprint -I/usr/include/boost -I/usr/include/cppunit -O0 -g3 -Wall -c -fmessage-length=0 -std=gnu++0x -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


