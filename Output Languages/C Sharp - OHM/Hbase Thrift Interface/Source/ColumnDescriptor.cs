/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using Thrift;
using Thrift.Protocol;
using Thrift.Transport;
namespace Apache.Hadoop.Hbase.Thrift
{
  public class ColumnDescriptor 
  {
    public byte[] name;
    public int maxVersions;
    public string compression;
    public bool inMemory;
    public int maxValueLength;
    public string bloomFilterType;
    public int bloomFilterVectorSize;
    public int bloomFilterNbHashes;
    public bool blockCacheEnabled;
    public int timeToLive;

    public Isset __isset;
    public struct Isset {
      public bool name;
      public bool maxVersions;
      public bool compression;
      public bool inMemory;
      public bool maxValueLength;
      public bool bloomFilterType;
      public bool bloomFilterVectorSize;
      public bool bloomFilterNbHashes;
      public bool blockCacheEnabled;
      public bool timeToLive;
    }

    public ColumnDescriptor() {
      this.maxVersions = 3;
      this.compression = "NONE";
      this.inMemory = false;
      this.maxValueLength = 2147483647;
      this.bloomFilterType = "NONE";
      this.bloomFilterVectorSize = 0;
      this.bloomFilterNbHashes = 0;
      this.blockCacheEnabled = false;
      this.timeToLive = -1;
    }

    public void Read (TProtocol iprot)
    {
      TField field;
      TStruct struc = iprot.ReadStructBegin();
      while (true)
      {
        field = iprot.ReadFieldBegin();
        if (field.Type == TType.Stop) { 
          break;
        }
        switch (field.ID)
        {
          case 1:
            if (field.Type == TType.String) {
              this.name = iprot.ReadBinary();
              this.__isset.name = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 2:
            if (field.Type == TType.I32) {
              this.maxVersions = iprot.ReadI32();
              this.__isset.maxVersions = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 3:
            if (field.Type == TType.String) {
              this.compression = iprot.ReadString();
              this.__isset.compression = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 4:
            if (field.Type == TType.Bool) {
              this.inMemory = iprot.ReadBool();
              this.__isset.inMemory = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 5:
            if (field.Type == TType.I32) {
              this.maxValueLength = iprot.ReadI32();
              this.__isset.maxValueLength = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 6:
            if (field.Type == TType.String) {
              this.bloomFilterType = iprot.ReadString();
              this.__isset.bloomFilterType = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 7:
            if (field.Type == TType.I32) {
              this.bloomFilterVectorSize = iprot.ReadI32();
              this.__isset.bloomFilterVectorSize = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 8:
            if (field.Type == TType.I32) {
              this.bloomFilterNbHashes = iprot.ReadI32();
              this.__isset.bloomFilterNbHashes = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 9:
            if (field.Type == TType.Bool) {
              this.blockCacheEnabled = iprot.ReadBool();
              this.__isset.blockCacheEnabled = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          case 10:
            if (field.Type == TType.I32) {
              this.timeToLive = iprot.ReadI32();
              this.__isset.timeToLive = true;
            } else { 
              TProtocolUtil.Skip(iprot, field.Type);
            }
            break;
          default: 
            TProtocolUtil.Skip(iprot, field.Type);
            break;
        }
        iprot.ReadFieldEnd();
      }
      iprot.ReadStructEnd();
    }

    public void Write(TProtocol oprot) {
      TStruct struc = new TStruct("ColumnDescriptor");
      oprot.WriteStructBegin(struc);
      TField field = new TField();
      if (this.name != null) {
        field.Name = "name";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteBinary(this.name);
        oprot.WriteFieldEnd();
      }
      field.Name = "maxVersions";
      field.Type = TType.I32;
      field.ID = 2;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(this.maxVersions);
      oprot.WriteFieldEnd();
      if (this.compression != null) {
        field.Name = "compression";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(this.compression);
        oprot.WriteFieldEnd();
      }
      field.Name = "inMemory";
      field.Type = TType.Bool;
      field.ID = 4;
      oprot.WriteFieldBegin(field);
      oprot.WriteBool(this.inMemory);
      oprot.WriteFieldEnd();
      field.Name = "maxValueLength";
      field.Type = TType.I32;
      field.ID = 5;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(this.maxValueLength);
      oprot.WriteFieldEnd();
      if (this.bloomFilterType != null) {
        field.Name = "bloomFilterType";
        field.Type = TType.String;
        field.ID = 6;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(this.bloomFilterType);
        oprot.WriteFieldEnd();
      }
      field.Name = "bloomFilterVectorSize";
      field.Type = TType.I32;
      field.ID = 7;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(this.bloomFilterVectorSize);
      oprot.WriteFieldEnd();
      field.Name = "bloomFilterNbHashes";
      field.Type = TType.I32;
      field.ID = 8;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(this.bloomFilterNbHashes);
      oprot.WriteFieldEnd();
      field.Name = "blockCacheEnabled";
      field.Type = TType.Bool;
      field.ID = 9;
      oprot.WriteFieldBegin(field);
      oprot.WriteBool(this.blockCacheEnabled);
      oprot.WriteFieldEnd();
      field.Name = "timeToLive";
      field.Type = TType.I32;
      field.ID = 10;
      oprot.WriteFieldBegin(field);
      oprot.WriteI32(this.timeToLive);
      oprot.WriteFieldEnd();
      oprot.WriteFieldStop();
      oprot.WriteStructEnd();
    }

    public override string ToString() {
      StringBuilder sb = new StringBuilder("ColumnDescriptor(");
      sb.Append("name: ");
      sb.Append(this.name);
      sb.Append(",maxVersions: ");
      sb.Append(this.maxVersions);
      sb.Append(",compression: ");
      sb.Append(this.compression);
      sb.Append(",inMemory: ");
      sb.Append(this.inMemory);
      sb.Append(",maxValueLength: ");
      sb.Append(this.maxValueLength);
      sb.Append(",bloomFilterType: ");
      sb.Append(this.bloomFilterType);
      sb.Append(",bloomFilterVectorSize: ");
      sb.Append(this.bloomFilterVectorSize);
      sb.Append(",bloomFilterNbHashes: ");
      sb.Append(this.bloomFilterNbHashes);
      sb.Append(",blockCacheEnabled: ");
      sb.Append(this.blockCacheEnabled);
      sb.Append(",timeToLive: ");
      sb.Append(this.timeToLive);
      sb.Append(")");
      return sb.ToString();
    }

  }

}