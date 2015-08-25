################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../obslugaCzytnika/test/AdapterObrazkaTest.cpp 

OBJS += \
./obslugaCzytnika/test/AdapterObrazkaTest.o 

CPP_DEPS += \
./obslugaCzytnika/test/AdapterObrazkaTest.d 


# Each subdirectory must supply rules for building sources it contributes
obslugaCzytnika/test/%.o: ../obslugaCzytnika/test/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/include/libfprint -I/usr/include/boost -O0 -g3 -Wall -c -fmessage-length=0 -std=gnu++0x -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


