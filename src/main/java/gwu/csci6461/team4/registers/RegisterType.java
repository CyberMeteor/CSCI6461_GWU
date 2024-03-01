package gwu.csci6461.team4.registers;

public enum RegisterType {
     //This enum class, defines types of register and number of bits in the register

    GeneralPurposeRegister0("GPR0", 16),
    GeneralPurposeRegister1("GPR1", 16),
    GeneralPurposeRegister2("GPR2", 16),
    GeneralPurposeRegister3("GPR3", 16),
    IndexRegister1("X1", 16),
    IndexRegister2("X2", 16),
    IndexRegister3("X3", 16),
    InstructionRegister("IR", 16),
    MemoryAddressRegister("MAR", 12),
    MemoryFaultRegister("MFR", 4),
    MemoryBufferRegister("MBR", 16),
    ProgramCounter("PC", 12),
    FloatingPointRegister("FR", 16),
    HLT("HLT", 1),
    ConditionCode("CC", 1);

    private String type;
    private int size;

    RegisterType(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }
}

