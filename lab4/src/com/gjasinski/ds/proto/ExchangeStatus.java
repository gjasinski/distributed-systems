// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/ExchangeServiceInterface.proto

package com.gjasinski.ds.proto;

/**
 * Protobuf type {@code ExchangeStatus}
 */
public  final class ExchangeStatus extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ExchangeStatus)
    ExchangeStatusOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ExchangeStatus.newBuilder() to construct.
  private ExchangeStatus(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ExchangeStatus() {
    currency_ = 0;
    buy_ = 0D;
    sell_ = 0D;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ExchangeStatus(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 8: {
            int rawValue = input.readEnum();

            currency_ = rawValue;
            break;
          }
          case 17: {

            buy_ = input.readDouble();
            break;
          }
          case 25: {

            sell_ = input.readDouble();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.gjasinski.ds.proto.ExchangeServiceInterface.internal_static_ExchangeStatus_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.gjasinski.ds.proto.ExchangeServiceInterface.internal_static_ExchangeStatus_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.gjasinski.ds.proto.ExchangeStatus.class, com.gjasinski.ds.proto.ExchangeStatus.Builder.class);
  }

  public static final int CURRENCY_FIELD_NUMBER = 1;
  private int currency_;
  /**
   * <code>.Currency currency = 1;</code>
   */
  public int getCurrencyValue() {
    return currency_;
  }
  /**
   * <code>.Currency currency = 1;</code>
   */
  public com.gjasinski.ds.proto.Currency getCurrency() {
    com.gjasinski.ds.proto.Currency result = com.gjasinski.ds.proto.Currency.valueOf(currency_);
    return result == null ? com.gjasinski.ds.proto.Currency.UNRECOGNIZED : result;
  }

  public static final int BUY_FIELD_NUMBER = 2;
  private double buy_;
  /**
   * <code>double buy = 2;</code>
   */
  public double getBuy() {
    return buy_;
  }

  public static final int SELL_FIELD_NUMBER = 3;
  private double sell_;
  /**
   * <code>double sell = 3;</code>
   */
  public double getSell() {
    return sell_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (currency_ != com.gjasinski.ds.proto.Currency.PLN.getNumber()) {
      output.writeEnum(1, currency_);
    }
    if (buy_ != 0D) {
      output.writeDouble(2, buy_);
    }
    if (sell_ != 0D) {
      output.writeDouble(3, sell_);
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (currency_ != com.gjasinski.ds.proto.Currency.PLN.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, currency_);
    }
    if (buy_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, buy_);
    }
    if (sell_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(3, sell_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.gjasinski.ds.proto.ExchangeStatus)) {
      return super.equals(obj);
    }
    com.gjasinski.ds.proto.ExchangeStatus other = (com.gjasinski.ds.proto.ExchangeStatus) obj;

    boolean result = true;
    result = result && currency_ == other.currency_;
    result = result && (
        java.lang.Double.doubleToLongBits(getBuy())
        == java.lang.Double.doubleToLongBits(
            other.getBuy()));
    result = result && (
        java.lang.Double.doubleToLongBits(getSell())
        == java.lang.Double.doubleToLongBits(
            other.getSell()));
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CURRENCY_FIELD_NUMBER;
    hash = (53 * hash) + currency_;
    hash = (37 * hash) + BUY_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getBuy()));
    hash = (37 * hash) + SELL_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getSell()));
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.gjasinski.ds.proto.ExchangeStatus parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.gjasinski.ds.proto.ExchangeStatus prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ExchangeStatus}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ExchangeStatus)
      com.gjasinski.ds.proto.ExchangeStatusOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.gjasinski.ds.proto.ExchangeServiceInterface.internal_static_ExchangeStatus_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.gjasinski.ds.proto.ExchangeServiceInterface.internal_static_ExchangeStatus_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.gjasinski.ds.proto.ExchangeStatus.class, com.gjasinski.ds.proto.ExchangeStatus.Builder.class);
    }

    // Construct using com.gjasinski.ds.proto.ExchangeStatus.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      currency_ = 0;

      buy_ = 0D;

      sell_ = 0D;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.gjasinski.ds.proto.ExchangeServiceInterface.internal_static_ExchangeStatus_descriptor;
    }

    public com.gjasinski.ds.proto.ExchangeStatus getDefaultInstanceForType() {
      return com.gjasinski.ds.proto.ExchangeStatus.getDefaultInstance();
    }

    public com.gjasinski.ds.proto.ExchangeStatus build() {
      com.gjasinski.ds.proto.ExchangeStatus result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.gjasinski.ds.proto.ExchangeStatus buildPartial() {
      com.gjasinski.ds.proto.ExchangeStatus result = new com.gjasinski.ds.proto.ExchangeStatus(this);
      result.currency_ = currency_;
      result.buy_ = buy_;
      result.sell_ = sell_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.gjasinski.ds.proto.ExchangeStatus) {
        return mergeFrom((com.gjasinski.ds.proto.ExchangeStatus)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.gjasinski.ds.proto.ExchangeStatus other) {
      if (other == com.gjasinski.ds.proto.ExchangeStatus.getDefaultInstance()) return this;
      if (other.currency_ != 0) {
        setCurrencyValue(other.getCurrencyValue());
      }
      if (other.getBuy() != 0D) {
        setBuy(other.getBuy());
      }
      if (other.getSell() != 0D) {
        setSell(other.getSell());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.gjasinski.ds.proto.ExchangeStatus parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.gjasinski.ds.proto.ExchangeStatus) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int currency_ = 0;
    /**
     * <code>.Currency currency = 1;</code>
     */
    public int getCurrencyValue() {
      return currency_;
    }
    /**
     * <code>.Currency currency = 1;</code>
     */
    public Builder setCurrencyValue(int value) {
      currency_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.Currency currency = 1;</code>
     */
    public com.gjasinski.ds.proto.Currency getCurrency() {
      com.gjasinski.ds.proto.Currency result = com.gjasinski.ds.proto.Currency.valueOf(currency_);
      return result == null ? com.gjasinski.ds.proto.Currency.UNRECOGNIZED : result;
    }
    /**
     * <code>.Currency currency = 1;</code>
     */
    public Builder setCurrency(com.gjasinski.ds.proto.Currency value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      currency_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.Currency currency = 1;</code>
     */
    public Builder clearCurrency() {
      
      currency_ = 0;
      onChanged();
      return this;
    }

    private double buy_ ;
    /**
     * <code>double buy = 2;</code>
     */
    public double getBuy() {
      return buy_;
    }
    /**
     * <code>double buy = 2;</code>
     */
    public Builder setBuy(double value) {
      
      buy_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double buy = 2;</code>
     */
    public Builder clearBuy() {
      
      buy_ = 0D;
      onChanged();
      return this;
    }

    private double sell_ ;
    /**
     * <code>double sell = 3;</code>
     */
    public double getSell() {
      return sell_;
    }
    /**
     * <code>double sell = 3;</code>
     */
    public Builder setSell(double value) {
      
      sell_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double sell = 3;</code>
     */
    public Builder clearSell() {
      
      sell_ = 0D;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ExchangeStatus)
  }

  // @@protoc_insertion_point(class_scope:ExchangeStatus)
  private static final com.gjasinski.ds.proto.ExchangeStatus DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.gjasinski.ds.proto.ExchangeStatus();
  }

  public static com.gjasinski.ds.proto.ExchangeStatus getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ExchangeStatus>
      PARSER = new com.google.protobuf.AbstractParser<ExchangeStatus>() {
    public ExchangeStatus parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ExchangeStatus(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ExchangeStatus> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ExchangeStatus> getParserForType() {
    return PARSER;
  }

  public com.gjasinski.ds.proto.ExchangeStatus getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

