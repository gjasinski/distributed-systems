// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/ExchangeServiceInterface.proto

package com.gjasinski.ds.proto;

public final class ExchangeServiceInterface {
  private ExchangeServiceInterface() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ExchangeStatus_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ExchangeStatus_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CurrencyCollection_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CurrencyCollection_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n$proto/ExchangeServiceInterface.proto\"H" +
      "\n\016ExchangeStatus\022\033\n\010currency\030\001 \001(\0162\t.Cur" +
      "rency\022\013\n\003buy\030\002 \001(\001\022\014\n\004sell\030\003 \001(\001\"1\n\022Curr" +
      "encyCollection\022\033\n\010currency\030\001 \003(\0162\t.Curre" +
      "ncy*.\n\010Currency\022\007\n\003PLN\020\000\022\007\n\003EUR\020\001\022\007\n\003USD" +
      "\020\002\022\007\n\003GBP\020\0032V\n\017ExchangeService\022C\n\027GetExc" +
      "hangeStatusStream\022\023.CurrencyCollection\032\017" +
      ".ExchangeStatus\"\0000\001B4\n\026com.gjasinski.ds." +
      "protoB\030ExchangeServiceInterfaceP\001b\006proto" +
      "3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_ExchangeStatus_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ExchangeStatus_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ExchangeStatus_descriptor,
        new java.lang.String[] { "Currency", "Buy", "Sell", });
    internal_static_CurrencyCollection_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_CurrencyCollection_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CurrencyCollection_descriptor,
        new java.lang.String[] { "Currency", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
