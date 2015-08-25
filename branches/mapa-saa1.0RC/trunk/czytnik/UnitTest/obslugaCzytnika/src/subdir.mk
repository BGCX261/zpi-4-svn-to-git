################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../obslugaCzytnika/src/AdapterCzytnika.cpp \
../obslugaCzytnika/src/AdapterObrazka.cpp 

OBJS += \
./obslugaCzytnika/src/AdapterCzytnika.o \
./obslugaCzytnika/src/AdapterObrazka.o 

CPP_DEPS += \
./obslugaCzytnika/src/AdapterCzytnika.d \
./obslugaCzytnika/src/AdapterObrazka.d 


# Each subdirectory must supply rules for building sources it contributes
obslugaCzytnika/src/%.o: ../obslugaCzytnika/src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -DUNIT_TEST -I/usr/include/libfprint -I/usr/include/boost -I/usr/include/cppunit -O0 -g3 -Wall -c -fmessage-length=0 -std=gnu++0x -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


