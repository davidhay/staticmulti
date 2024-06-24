package com.demo.static_multi.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

class ProcessorTest {
    
    static final List<Integer> VALUES = List.of(1,2,3,4,5);
    
    Processor processor;
    
    @BeforeEach
    void setup() {
        processor = new Processor();    
    }

    @Nested
    class ImplOne {

        @Test
        void testProcessAsIs() {
            assertThat(processor.process1(VALUES)).isEqualTo(List.of(2, 4, 6, 8, 10));
        }

        @Test
        void testProcessMockStatic() {
            try (MockedStatic<SizeUtils> mockedStatic = Mockito.mockStatic(SizeUtils.class)) {
                mockedStatic.when(() -> SizeUtils.increase(any(Integer.class))).thenReturn(2);
                assertThat(processor.process1(VALUES)).isEqualTo(List.of(2, 2, 2, 2, 2));
                mockedStatic.verify(() -> SizeUtils.increase(1), times(1));
                mockedStatic.verify(() -> SizeUtils.increase(2), times(1));
                mockedStatic.verify(() -> SizeUtils.increase(3), times(1));
                mockedStatic.verify(() -> SizeUtils.increase(4), times(1));
                mockedStatic.verify(() -> SizeUtils.increase(5), times(1));
                mockedStatic.verifyNoMoreInteractions();
            }
        }
    }
    @Nested
    class ImplTwo {

        @Test
        void testProcessAsIs() {
            assertThat(processor.process2(VALUES)).isEqualTo(List.of(3,6,9,12,15));
        }

        @Test
        void testProcessMockStatic() {
            try (MockedStatic<SizeUtils> mockedStatic = Mockito.mockStatic(SizeUtils.class)) {
                mockedStatic.when(() -> SizeUtils.increase(any(Integer.class))).thenReturn(3);
                
                //mocked static ignored due to multi threading :-(
                assertThat(processor.process2(VALUES)).isEqualTo(List.of(3,6,9,12,15));
                
                mockedStatic.verify(() -> SizeUtils.increase(5), never());
                mockedStatic.verifyNoMoreInteractions();
            }
        }
    }


}