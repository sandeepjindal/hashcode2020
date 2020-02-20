using System;
using System.Collections.Generic;
using System.Text;

namespace HC
{
    public abstract class Writer
    {

    public string Line<T>(T[] values, string separator =" "){
    return string.Join(separator, values);
    }


  public void WriteFile(string filename, string[] lines){
   System.IO.File.WriteAllLines(filename, lines);
  }

        /// <summary>
        /// 
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="matrix">Jagged Array for the matrix</param>
        /// <param name="separator"></param>
        /// <param name="pad"></param>
        public static void WriteLog<T>(T[][] matrix, char separator = ' ', int pad = 1)
        {
            var sb = new StringBuilder();
            foreach (var row in matrix) {
                foreach (var cell in row)
                    sb.Append(padded<T>(cell.ToString(), separator, pad));
                sb.AppendLine();
            }
            Console.Write(sb);
        }

        public static string padded<T>(string value, char separator, int pad)
        {
            if (separator == '\0' || pad == 0) return value;
                var padSize = Math.Max(0, pad - value.Length);
            return separator + new string(' ', padSize) + value;
        }
    }
}

