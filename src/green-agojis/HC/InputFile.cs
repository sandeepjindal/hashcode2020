using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace HC
{
    public class InputFile
    {
        private string filename;
        private string separator;
        public InputFile(string filepath, string separator = " ") {
            this.filename = filepath;
            this.separator = separator;
            Lines = Load(filename);
        }

        public IList<string> Lines { get; private set; }

        public IList<string> Line(int row) => Cells(this.Lines[row], separator);

        public IList<string> Load(string filepath) => File.ReadAllLines(filepath);

        public IList<string> Cells(string line, string separator = " ") =>
            line.Split(separator, StringSplitOptions.RemoveEmptyEntries);

        public static string[] FilesInDir(string dir) {
            return Directory.GetFiles(dir);
        }

        public string Get(int row, int column) => Line(row)[column];

        public int GetInt(int row, int column) => int.Parse(Get(row, column));

        public int[][] GetMatrix(int skip=1){
        var rows = this.Lines.Count;
            var result = new int[rows][];
            for(var i = rows - skip; i >= 0; i--)
            {
                result[i] = Line(i).Select(x => int.Parse(x)).ToArray();
            }
            return result;
        }




    }
}
